package com.client.processingfeecalculator.util;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import com.client.processingfeecalculator.constant.FileType;

public class UtilTest {

    @Test
    public void shouldReturnOptionalFileTypeWhenFileHasValidExtension() {

        Assert.assertEquals(FileType.CSV, Util.getFileType("data /input data.csv").get());
    }

    @Test
    public void shouldReturnEmptyOptionalWhenFileDoesNotHaveExtension() {
        Assert.assertEquals(Optional.empty(), Util.getFileType("data /input data"));
    }

    @Test
    public void shouldReturnEmptyOptionalWhenFileDoesNotHaveValidExtension() {
        Assert.assertEquals(Optional.empty(), Util.getFileType("data /input data.pdf"));
    }
}
