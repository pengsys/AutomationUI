/*
 *  Copyright 2019 Qameta Software OÜ
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package common.report.common;

import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.*;
import io.qameta.allure.util.ExceptionUtils;
import io.qameta.allure.util.ResultsUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * The class contains some useful methods to work with {@link AllureLifecycle}.
 */
@SuppressWarnings({"PMD.ClassNamingConventions", "PMD.ExcessivePublicCount", "PMD.TooManyMethods"})
public class MyAllure {

    private static String TXT_EXTENSION = ".txt";
    private static String TEXT_PLAIN = "text/plain";

    private static MyAllureLifecycle lifecycle;

    /**
     * Do not instance.
     */
    private MyAllure() {
        throw new IllegalStateException("Do not instance");
    }

    /**
     * Returns {@link AllureLifecycle} for low level operations with results.
     *
     * @return the lifecycle.
     */
    public static MyAllureLifecycle getLifecycle() {
        if (Objects.isNull(lifecycle)) {
            lifecycle = new MyAllureLifecycle();
        }
        return lifecycle;
    }


    public static void setLifecycle(AllureLifecycle lifecycle) {
        lifecycle = lifecycle;
    }

    /**
     * Adds passed step with provided name in current test or step (or test fixture). Takes no effect
     * if no test run at the moment. Shortcut for {@link #step(String, Status)}.
     *
     * @param name the name of step.
     */
    public static void step(String name) {
        step(name, Status.PASSED);
    }

    /**
     * Adds step with provided name and status in current test or step (or test fixture). Takes no effect
     * if no test run at the moment.
     *
     * @param name   the name of step.
     * @param status the step status.
     */
    public static void step(String name, Status status) {
        String uuid = UUID.randomUUID().toString();
        getLifecycle().startStep(uuid, new StepResult().setName(name).setStatus(status));
        getLifecycle().stopStep(uuid);
    }

    /**
     * Syntax sugar for {@link #step(String, ThrowableRunnable)}.
     *
     * @param name     the name of step.
     * @param runnable the step's body.
     */
    public static void step(String name, ThrowableRunnableVoid runnable) {
        step(name, () -> {
            runnable.run();
            return null;
        });
    }

    /**
     * Syntax sugar for {@link #step(ThrowableContextRunnable)}.
     *
     * @param name     the name of step.
     * @param runnable the step's body.
     */
    public static <T> T step(String name, ThrowableRunnable<T> runnable) {
        return step(step -> {
            step.name(name);
            return runnable.run();
        });
    }

    /**
     * Syntax sugar for {@link #step(ThrowableContextRunnable)}.
     *
     * @param runnable the step's body.
     */
    public static void step(ThrowableContextRunnableVoid<StepContext> runnable) {
        step(step -> {
            runnable.run(step);
            return null;
        });
    }

    /**
     * Syntax sugar for {@link #step(ThrowableContextRunnable)}.
     *
     * @param name     the name of step.
     * @param runnable the step's body.
     */
    public static void step(String name, ThrowableContextRunnableVoid<StepContext> runnable) {
        step(step -> {
            step.name(name);
            runnable.run(step);
            return null;
        });
    }

    /**
     * Syntax sugar for {@link #step(ThrowableContextRunnable)}.
     *
     * @param name     the name of step.
     * @param runnable the step's body.
     */
    public static <T> T step(String name, ThrowableContextRunnable<T, StepContext> runnable) {
        return step(step -> {
            step.name(name);
            return runnable.run(step);
        });
    }

    /**
     * Run provided {@link ThrowableRunnable} as step with given name. Takes no effect
     * if no test run at the moment.
     *
     * @param runnable the step's body.
     */
    public static <T> T step(ThrowableContextRunnable<T, StepContext> runnable) {
        String uuid = UUID.randomUUID().toString();
        getLifecycle().startStep(uuid, new StepResult().setName("step"));

        Object var3;
        try {
            T result = runnable.run(new DefaultStepContext(uuid));
            getLifecycle().updateStep(uuid, step -> step.setStatus(Status.PASSED));
            var3 = result;
            return (T) var3;
        } catch (Throwable var7) {
            getLifecycle().updateStep((s) -> {
                s.setStatus((Status) ResultsUtils.getStatus(var7).orElse(Status.BROKEN)).setStatusDetails((StatusDetails) ResultsUtils.getStatusDetails(var7).orElse(null));
            });
            ExceptionUtils.sneakyThrow(var7);
            var3 = null;
        } finally {
            getLifecycle().stopStep(uuid);
        }
        return (T) var3;
    }

    /**
     * Adds epic label to current test or step (or fixture) if any. Takes no effect
     * if no test run at the moment. Shortcut for {@link #label(String, String)}.
     *
     * @param value the value of label.
     */
    public static void epic(String value) {
        label("epic", value);
    }

    /**
     * Adds feature label to current test or step (or fixture) if any. Takes no effect
     * if no test run at the moment. Shortcut for {@link #label(String, String)}.
     *
     * @param value the value of label.
     */
    public static void feature(String value) {
        label("feature", value);
    }

    /**
     * Adds story label to current test or step (or fixture) if any. Takes no effect
     * if no test run at the moment. Shortcut for {@link #label(String, String)}.
     *
     * @param value the value of label.
     */
    public static void story(String value) {
        label("story", value);
    }

    /**
     * Adds suite label to current test or step (or fixture) if any. Takes no effect
     * if no test run at the moment. Shortcut for {@link #label(String, String)}.
     *
     * @param value the value of label.
     */
    public static void suite(String value) {
        label("suite", value);
    }

    /**
     * Adds label to current test or step (or fixture) if any. Takes no effect
     * if no test run at the moment.
     *
     * @param name  the name of label.
     * @param value the value of label.
     */
    public static void label(String name, String value) {
        Label label = new Label().setName(name).setValue(value);
        getLifecycle().updateTestCase(testResult -> testResult.getLabels().add(label));
    }

    /**
     * Adds parameter to current test or step (or fixture) if any. Takes no effect
     * if no test run at the moment.
     *
     * @param name  the name of parameter.
     * @param value the value of parameter.
     */
    public static <T> T parameter(String name, T value) {
        Parameter parameter = ResultsUtils.createParameter(name, value);
        getLifecycle().updateTestCase(testResult -> testResult.getParameters().add(parameter));
        return value;
    }

    /**
     * Adds issue link to current test or step (or fixture) if any. Takes no effect
     * if no test run at the moment. Shortcut for {@link #link(String, String, String)}.
     *
     * @param name the name of link.
     * @param url  the link's url.
     */
    public static void issue(String name, String url) {
        link(name, "issue", url);
    }

    /**
     * Adds tms link to current test or step (or fixture) if any. Takes no effect
     * if no test run at the moment. Shortcut for {@link #link(String, String, String)}.
     *
     * @param name the name of link.
     * @param url  the link's url.
     */
    public static void tms(String name, String url) {
        link(name, "tms", url);
    }

    /**
     * Adds link to current test or step (or fixture) if any. Takes no effect
     * if no test run at the moment. Shortcut for {@link #link(String, String)}
     *
     * @param url the link's url.
     */
    public static void link(String url) {
        link(null, url);
    }

    /**
     * Adds link to current test or step (or fixture) if any. Takes no effect
     * if no test run at the moment. Shortcut for {@link #link(String, String, String)}
     *
     * @param name the name of link.
     * @param url  the link's url.
     */
    public static void link(String name, String url) {
        link(name, null, url);
    }

    /**
     * Adds link to current test or step (or fixture) if any. Takes no effect
     * if no test run at the moment.
     *
     * @param name the name of link.
     * @param type the type of link, used to display link icon in the report.
     * @param url  the link's url.
     */
    public static void link(String name, String type, String url) {
        Link link = new Link().setName(name).setType(type).setUrl(url);
        getLifecycle().updateTestCase(testResult -> testResult.getLinks().add(link));
    }

    /**
     * Adds description to current test or step (or fixture) if any. Takes no effect
     * if no test run at the moment. Expecting description provided in Markdown format.
     *
     * @param description the description in markdown format.
     * @see #descriptionHtml(String)
     */
    public static void description(String description) {
        getLifecycle().updateTestCase(executable -> executable.setDescription(description));
    }

    /**
     * Adds descriptionHtml to current test or step (or fixture) if any. Takes no effect
     * if no test run at the moment. Note that description will take no effect if descriptionHtml is
     * specified.
     *
     * @param descriptionHtml the description in html format.
     * @see #description(String)
     */
    public static void descriptionHtml(String descriptionHtml) {
        getLifecycle().updateTestCase(executable -> executable.setDescriptionHtml(descriptionHtml));
    }

    /**
     * Adds attachment.
     *
     * @param name    the name of attachment.
     * @param content the attachment content.
     */
    public static void attachment(String name, String content) {
        addAttachment(name, content);
    }

    /**
     * Adds attachment.
     *
     * @param name    the name of attachment.
     * @param content the stream that contains attachment content.
     */
    public static void attachment(String name, InputStream content) {
        addAttachment(name, content);
    }

    /**
     * @deprecated use {@link #label(String, String)} instead.
     */
    @Deprecated
    public static void addLabels(Label... labels) {
        getLifecycle().updateTestCase((testResult) -> {
            testResult.getLabels().addAll(Arrays.asList(labels));
        });
    }

    /**
     * @deprecated use {@link #link(String, String, String)} instead.
     */
    @Deprecated
    public static void addLinks(Link... links) {
        getLifecycle().updateTestCase(testResult -> {
            testResult.getLinks().addAll(Arrays.asList(links));
        });
    }

    /**
     * @deprecated use {@link #description(String)} instead.
     */
    @Deprecated
    public static void addDescription(String description) {
        description(description);
    }

    /**
     * @deprecated use {@link #descriptionHtml(String)} instead.
     */
    @Deprecated
    public static void addDescriptionHtml(String descriptionHtml) {
        descriptionHtml(descriptionHtml);
    }

    public static void addAttachment(String name, String content) {
        getLifecycle().addAttachment(name, TEXT_PLAIN, TXT_EXTENSION, content.getBytes(StandardCharsets.UTF_8));
    }

    public static void addAttachment(String name, String type, String content) {
        getLifecycle().addAttachment(name, type, TXT_EXTENSION, content.getBytes(StandardCharsets.UTF_8));
    }

    @SuppressWarnings("PMD.UseObjectForClearerAPI")
    public static void addAttachment(String name, String type,
                                     String content, String fileExtension) {
        getLifecycle().addAttachment(name, type, fileExtension, content.getBytes(StandardCharsets.UTF_8));
    }

    public static void addAttachment(String name, InputStream content) {
        getLifecycle().addAttachment(name, null, null, content);
    }

    @SuppressWarnings("PMD.UseObjectForClearerAPI")
    public static void addAttachment(String name, String type,
                                     InputStream content, String fileExtension) {
        getLifecycle().addAttachment(name, type, fileExtension, content);
    }

    public static CompletableFuture<byte[]> addByteAttachmentAsync(
            String name, String type, Supplier<byte[]> body) {
        return addByteAttachmentAsync(name, type, "", body);
    }

    public static CompletableFuture<byte[]> addByteAttachmentAsync(
            String name, String type, String fileExtension, Supplier<byte[]> body) {
        String source = getLifecycle().prepareAttachment(name, type, fileExtension);
        return CompletableFuture.supplyAsync(body).whenComplete((result, ex) -> {
            getLifecycle().writeAttachment(source, new ByteArrayInputStream(result));
        });
    }

    public static CompletableFuture<InputStream> addStreamAttachmentAsync(
            String name, String type, Supplier<InputStream> body) {
        return addStreamAttachmentAsync(name, type, "", body);
    }

    public static CompletableFuture<InputStream> addStreamAttachmentAsync(
            String name, String type, String fileExtension, Supplier<InputStream> body) {
        String source = lifecycle.prepareAttachment(name, type, fileExtension);
        return CompletableFuture.supplyAsync(body).whenComplete((result, ex) -> {
            lifecycle.writeAttachment(source, result);
        });
    }

    /**
     * Runnable that allows to throw an exception and return any type.
     *
     * @param <T> the type of return value.
     */
    @FunctionalInterface
    public interface ThrowableRunnable<T> {

        T run() throws Throwable;

    }

    /**
     * Runnable that allows to throw an exception and return void.
     */
    @FunctionalInterface
    public interface ThrowableRunnableVoid {

        void run() throws Throwable;

    }

    /**
     * Runnable that allows to throw an exception and return any type.
     *
     * @param <T> the type of return value.
     * @param <U> the type of context.
     */
    @FunctionalInterface
    public interface ThrowableContextRunnable<T, U> {

        T run(U var1) throws Throwable;

    }

    /**
     * Callable that allows to throw an exception and return void.
     *
     * @param <T> the type of context.
     */
    @FunctionalInterface
    public interface ThrowableContextRunnableVoid<T> {

        void run(T var1) throws Throwable;

    }

    /**
     * Step context.
     */
    public interface StepContext {

        void name(String var1);

        <T> T parameter(String var1, T var2);

    }

    /**
     * Basic implementation of step context.
     */
    private static class DefaultStepContext implements StepContext {

        private String uuid;

        private DefaultStepContext(String uuid) {
            this.uuid = uuid;
        }

        @Override
        public void name(String name) {
            getLifecycle().updateStep(uuid, stepResult -> stepResult.setName(name));
        }

        @Override
        public <T> T parameter(String name, T value) {
            Parameter param = ResultsUtils.createParameter(name, value);
            getLifecycle().updateStep(uuid, stepResult -> stepResult.getParameters().add(param));
            return value;
        }
    }

}
