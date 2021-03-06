#include <stdio.h>

#include <com_cornucopia_patch_BSPatch.h>

#include <stdlib.h>
#include <err.h>
#include <unistd.h>
#include <fcntl.h>

#include <assert.h>
#include <android/log.h>

#include <sys/stat.h>

#include "../libzip2/bzlib.h"

static off_t offtin(u_char *buf) {
	off_t y;

	y = buf[7] & 0x7F;
	y = y * 256;
	y += buf[6];
	y = y * 256;
	y += buf[5];
	y = y * 256;
	y += buf[4];
	y = y * 256;
	y += buf[3];
	y = y * 256;
	y += buf[2];
	y = y * 256;
	y += buf[1];
	y = y * 256;
	y += buf[0];

	if (buf[7] & 0x80)
		y = -y;

	return y;
}

int applypatch(int argc, char * argv[]) {
	FILE * f, *cpf, *dpf, *epf;
	BZFILE * cpfbz2, *dpfbz2, *epfbz2;
	int cbz2err, dbz2err, ebz2err;
	int fd;
	ssize_t oldsize, newsize;
	ssize_t bzctrllen, bzdatalen;
	u_char header[32], buf[8];
	u_char *oldf, *newf;
	off_t oldpos, newpos;
	off_t ctrl[3];
	off_t lenread;
	off_t i;

	if (argc != 4)
		errx(1, "usage: %s oldfile newfile patchfile\n", argv[0]);

	/* Open patch file */
	if ((f = fopen(argv[3], "r")) == NULL)
		err(1, "fopen(%s)", argv[3]);

	/* Read header */
	if (fread(header, 1, 32, f) < 32) {
		if (feof(f))
			errx(1, "Corrupt patch\n");
		err(1, "fread(%s)", argv[3]);
	}

	/* Check for appropriate magic */
	if (memcmp(header, "BSDIFF40", 8) != 0)
		errx(1, "Corrupt patch\n");

	/* Read lengths from header */
	bzctrllen = offtin(header + 8);
	bzdatalen = offtin(header + 16);
	newsize = offtin(header + 24);
	if ((bzctrllen < 0) || (bzdatalen < 0) || (newsize < 0))
		errx(1, "Corrupt patch\n");

	/* Close patch file and re-open it via libbzip2 at the right places */
	if (fclose(f))
		err(1, "fclose(%s)", argv[3]);
	if ((cpf = fopen(argv[3], "r")) == NULL)
		err(1, "fopen(%s)", argv[3]);
	if (fseeko(cpf, 32, SEEK_SET))
		err(1, "fseeko(%s, %lld)", argv[3], (long long) 32);
	if ((cpfbz2 = BZ2_bzReadOpen(&cbz2err, cpf, 0, 0, NULL, 0)) == NULL)
		errx(1, "BZ2_bzReadOpen, bz2err = %d", cbz2err);
	if ((dpf = fopen(argv[3], "r")) == NULL)
		err(1, "fopen(%s)", argv[3]);
	if (fseeko(dpf, 32 + bzctrllen, SEEK_SET))
		err(1, "fseeko(%s, %lld)", argv[3], (long long) (32 + bzctrllen));
	if ((dpfbz2 = BZ2_bzReadOpen(&dbz2err, dpf, 0, 0, NULL, 0)) == NULL)
		errx(1, "BZ2_bzReadOpen, bz2err = %d", dbz2err);
	if ((epf = fopen(argv[3], "r")) == NULL)
		err(1, "fopen(%s)", argv[3]);
	if (fseeko(epf, 32 + bzctrllen + bzdatalen, SEEK_SET))
		err(1, "fseeko(%s, %lld)", argv[3],
				(long long) (32 + bzctrllen + bzdatalen));
	if ((epfbz2 = BZ2_bzReadOpen(&ebz2err, epf, 0, 0, NULL, 0)) == NULL)
		errx(1, "BZ2_bzReadOpen, bz2err = %d", ebz2err);

	if (((fd = open(argv[1], O_RDONLY, 0)) < 0)
			|| ((oldsize = lseek(fd, 0, SEEK_END)) == -1)
			|| ((oldf = malloc(oldsize + 1)) == NULL)
			|| (lseek(fd, 0, SEEK_SET) != 0)
			|| (read(fd, oldf, oldsize) != oldsize) || (close(fd) == -1))
		err(1, "%s", argv[1]);
	if ((newf = malloc(newsize + 1)) == NULL)
		err(1, NULL);

	oldpos = 0;
	newpos = 0;
	while (newpos < newsize) {
		/* Read control data */
		for (i = 0; i <= 2; i++) {
			lenread = BZ2_bzRead(&cbz2err, cpfbz2, buf, 8);
			if ((lenread < 8)
					|| ((cbz2err != BZ_OK) && (cbz2err != BZ_STREAM_END)))
				errx(1, "Corrupt patch\n");
			ctrl[i] = offtin(buf);
		};

		/* Sanity-check */
		if (newpos + ctrl[0] > newsize)
			errx(1, "Corrupt patch\n");

		/* Read diff string */
		lenread = BZ2_bzRead(&dbz2err, dpfbz2, newf + newpos, ctrl[0]);
		if ((lenread < ctrl[0])
				|| ((dbz2err != BZ_OK) && (dbz2err != BZ_STREAM_END)))
			errx(1, "Corrupt patch\n");

		/* Add oldf data to diff string */
		for (i = 0; i < ctrl[0]; i++)
			if ((oldpos + i >= 0) && (oldpos + i < oldsize))
				newf[newpos + i] += oldf[oldpos + i];

		/* Adjust pointers */
		newpos += ctrl[0];
		oldpos += ctrl[0];

		/* Sanity-check */
		if (newpos + ctrl[1] > newsize)
			errx(1, "Corrupt patch\n");

		/* Read extra string */
		lenread = BZ2_bzRead(&ebz2err, epfbz2, newf + newpos, ctrl[1]);
		if ((lenread < ctrl[1])
				|| ((ebz2err != BZ_OK) && (ebz2err != BZ_STREAM_END)))
			errx(1, "Corrupt patch\n");

		/* Adjust pointers */
		newpos += ctrl[1];
		oldpos += ctrl[2];
	};

	/* Clean up the bzip2 reads */
	BZ2_bzReadClose(&cbz2err, cpfbz2);
	BZ2_bzReadClose(&dbz2err, dpfbz2);
	BZ2_bzReadClose(&ebz2err, epfbz2);
	if (fclose(cpf) || fclose(dpf) || fclose(epf))
		err(1, "fclose(%s)", argv[3]);

	/* Write the newf file */
	if (((fd = open(argv[2], O_CREAT | O_TRUNC | O_WRONLY, 0666)) < 0)
			|| (write(fd, newf, newsize) != newsize) || (close(fd) == -1))
		err(1, "%s", argv[2]);


	// if (chmod(argv[2], S_IRWXU | S_IRGRP | S_IXGRP | S_IROTH ) == -1)
	// {
	// 	err(1, "chmod(%s_", argv[2]);
	// }

	free(newf);
	free(oldf);

	return 0;
}

JNIEXPORT jint JNICALL Java_com_cornucopia_patch_BSPatch_mergePatch(
		JNIEnv * env, jclass obj, jstring oldf, jstring newf, jstring patch) {
	int argc = 4;
	char * argv[argc];
	argv[0] = "bspatch";
	argv[1] = (*env)->GetStringUTFChars(env, oldf, 0);  // c : (*env)->GetStringUTFChars(env, oldf, 0); cpp compile error
	argv[2] = (*env)->GetStringUTFChars(env, newf, 0);
	argv[3] = (*env)->GetStringUTFChars(env, patch, 0);

	// __android_log_print(ANDROID_LOG_INFO, "patch", "old = %s ", argv[1]);
	// __android_log_print(ANDROID_LOG_INFO, "patch", "new = %s ", argv[2]);
	// __android_log_print(ANDROID_LOG_INFO, "patch", "patch = %s ", argv[3]);

	int ret = applypatch(argc, argv);

	(*env)->ReleaseStringUTFChars(env, oldf, argv[1]);
	(*env)->ReleaseStringUTFChars(env, newf, argv[2]);
	(*env)->ReleaseStringUTFChars(env, patch, argv[3]);

	// __android_log_print(ANDROID_LOG_INFO, "patch", "ret = %d ", ret);

	return ret;
}

// ============ JNI_OnLoad ============== //
static int registerNativeMethods(JNIEnv* env , const char* className , JNINativeMethod* gMethods, int numMethods)
{
    jclass clazz;
    clazz = (*env)->FindClass(env, className);
    if (clazz == NULL)
    {
        return JNI_FALSE;
    }

    if ((*env)->RegisterNatives(env, clazz, gMethods, numMethods) < 0)
    {
        return JNI_FALSE;
    }

    return JNI_TRUE;
}

static JNINativeMethod methods[] = {
	// {java method name, java method signature, native method pointer}
	/*
		$ javap -s com.cornucopia.patch.BSPatch
		Compiled from "BSPatch.java"
		public class com.cornucopia.patch.BSPatch {
		  static {};
		    descriptor: ()V

		  public com.cornucopia.patch.BSPatch();
		    descriptor: ()V

		  public static native int mergePatch(java.lang.String, java.lang.String, java.lang.String);
		    descriptor: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I */
    {"mergePatch", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I", (void*)Java_com_cornucopia_patch_BSPatch_mergePatch},
};

static int registerNatives(JNIEnv* env)
{
    const char* kClassName = "com/cornucopia/patch/BSPatch";
    return registerNativeMethods(env, kClassName, methods,  sizeof(methods) / sizeof(methods[0]));
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved)
{
	JNIEnv* env = NULL;
	jint result = -1;

	if ((*vm) -> GetEnv(vm, (void**) &env, JNI_VERSION_1_4) != JNI_OK)
	{
		return -1;
	}

	assert(env != NULL);

	if (!registerNatives(env))
	{
		return -1;
	}

	return JNI_VERSION_1_4;

}
