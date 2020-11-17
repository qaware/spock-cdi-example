package de.qaware.spock.cdi.extension

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.SpecInfo

import javax.enterprise.inject.se.SeContainer
import javax.enterprise.inject.se.SeContainerInitializer
import javax.inject.Inject

class CdiExtension extends AbstractAnnotationDrivenExtension<Cdi> {
    @Override
    void visitSpecAnnotation(Cdi annotation, SpecInfo spec) {
        def interceptor = new MethodInterceptor()
        spec.addSharedInitializerInterceptor(interceptor)
        spec.addSetupInterceptor(interceptor)
        spec.addCleanupSpecInterceptor(interceptor)
    }

    class MethodInterceptor extends AbstractMethodInterceptor {

        SeContainer container

        @Override
        void interceptSharedInitializerMethod(IMethodInvocation invocation) throws Throwable {
            def specInstance = invocation.getInstance()

            container = SeContainerInitializer.newInstance()
                    .addBeanClasses(specInstance.getClass())
                    .initialize()

            invocation.proceed()
        }

        @Override
        void interceptSetupMethod(IMethodInvocation invocation) throws Throwable {
            def specInstance = invocation.getInstance()

            Arrays.stream(specInstance.getClass().getDeclaredFields())
                    .filter { f -> f.isAnnotationPresent(Inject) }
                    .forEach { f ->
                        f.setAccessible(true)
                        f.set(specInstance, container.select(f.getType()).get())
                    }

            invocation.proceed()
        }

        @Override
        void interceptCleanupSpecMethod(IMethodInvocation invocation) throws Throwable {
            container.close()

            invocation.proceed()
        }
    }
}
