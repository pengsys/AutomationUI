package common.report.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.report.bean.Attachments;
import io.qameta.allure.AllureResultsWriteException;
import io.qameta.allure.AllureResultsWriter;
import io.qameta.allure.model.Allure2ModelJackson;
import io.qameta.allure.model.TestResult;
import io.qameta.allure.model.TestResultContainer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

public class SystemResultsWriter implements AllureResultsWriter {
    private Path outputDirectory;
    private ObjectMapper mapper;
    private MongoWriter mongoWriter;
    private boolean saveToDB = false;

    public SystemResultsWriter(Path outputDirectory) {
        this.outputDirectory = outputDirectory;
        this.mapper = Allure2ModelJackson.createMapper();

        this.saveToDB = ("y").equalsIgnoreCase(System.getProperty("saveToDB")) ? true : false;
        if (this.saveToDB) {
            this.mongoWriter = MongoWriter.getMongoWriter(System.getProperty("mongoHost"), Integer.valueOf(System.getProperty("mongoPort")));
        }
    }

    @Override
    public void write(TestResult testResult) {
        String testResultName = Objects.isNull(testResult.getUuid()) ? generateTestResultName() : generateTestResultName(testResult.getUuid());
        this.createDirectories(this.outputDirectory);
        Path file = this.outputDirectory.resolve(testResultName);

        try {
            this.mapper.writeValue(file.toFile(), testResult);
            if (saveToDB) {
                mongoWriter.write("TestResult", testResult);
            }
        } catch (IOException var5) {
            throw new AllureResultsWriteException("Could not write Allure test result", var5);
        }
    }

    @Override
    public void write(TestResultContainer testResultContainer) {
        String testResultContainerName = Objects.isNull(testResultContainer.getUuid()) ? generateTestResultContainerName() : generateTestResultContainerName(testResultContainer.getUuid());
        this.createDirectories(this.outputDirectory);
        Path file = this.outputDirectory.resolve(testResultContainerName);

        try {
            this.mapper.writeValue(file.toFile(), testResultContainer);
            if (saveToDB) {
                mongoWriter.write("TestResultContainer", testResultContainer);
            }
        } catch (IOException var5) {
            throw new AllureResultsWriteException("Could not write Allure test result container", var5);
        }
    }

    @Override
    public void write(String source, InputStream attachment) {
        this.createDirectories(this.outputDirectory);
        Path file = this.outputDirectory.resolve(source);
        Attachments att = new Attachments();
        att.setName(source);
        att.setInputStream(attachment);
        try {
            InputStream is = attachment;
            Throwable var5 = null;
            try {
                Files.copy(is, file, new CopyOption[0]);
                if (saveToDB) {
                    mongoWriter.write("attachments", att);
                }

            } catch (Throwable var15) {
                var5 = var15;
                throw var15;
            } finally {
                if (attachment != null) {
                    if (var5 != null) {
                        try {
                            is.close();
                        } catch (Throwable var14) {
                            var5.addSuppressed(var14);
                        }
                    } else {
                        attachment.close();
                    }
                }
            }
        } catch (
                IOException var17) {
            throw new AllureResultsWriteException("Could not write Allure attachment", var17);
        }
    }

    private void createDirectories(Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException var3) {
            throw new AllureResultsWriteException("Could not create Allure results directory", var3);
        }
    }

    protected static String generateTestResultName() {
        return generateTestResultName(UUID.randomUUID().toString());
    }

    protected static String generateTestResultName(String uuid) {
        return uuid + "-result.json";
    }

    protected static String generateTestResultContainerName() {
        return generateTestResultContainerName(UUID.randomUUID().toString());
    }

    protected static String generateTestResultContainerName(String uuid) {
        return uuid + "-container.json";
    }
}
