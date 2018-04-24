
package com.cornucopia.tools

import com.android.build.api.transform.Context
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Project;


class TransformModule extends Transform {

    private final Project project

    public TransformModule(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "TransformModule"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider,
                   boolean isIncremental) throws IOException, TransformException, InterruptedException {
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental)
    }
}