package xyz.vottus.semicolonus.app;

import xyz.vottus.semicolonus.exception.InputNotFoundException;
import xyz.vottus.semicolonus.exception.NoWriteAccessException;
import xyz.vottus.semicolonus.exception.OutputAlreadyExistsException;
import xyz.vottus.semicolonus.exception.SemicolonusException;
import xyz.vottus.semicolonus.utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Semicolonus Processor
 *
 * Copyright (c) 2020 Filip Vottus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class SemicolonusProcessor {

    private final String inputFile;
    private final String outputFile;
    private final Boolean override;

    /**
     * SemicolonusProcessor constructor.
     * @param inputFile Input file
     * @param outputFile Output file
     * @param override Override output if exists?
     */
    public SemicolonusProcessor(String inputFile, String outputFile, boolean override) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.override = override;
        if (override && Utils.exists(outputFile)) {
            System.out.println("Override mode is enabled - the existing file will be overwritten by new contents... Starting in 5 seconds");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @throws SemicolonusException
     */
    public void process() throws SemicolonusException {
        if (!Utils.exists(inputFile)) {
            throw new InputNotFoundException("Input file doesn't exist.");
        } else {
            if (Utils.exists(outputFile)) {
                if (override) {
                    if (!Utils.deleteFile(outputFile)) {
                        throw new NoWriteAccessException("Can't delete existing output file.");
                    }
                } else {
                    throw new OutputAlreadyExistsException("Output file already exists.");
                }
            }
            if (!Utils.createFile(outputFile)) {
                throw new NoWriteAccessException("Can't create the output file.");
            }
            try {
                System.out.println("Writing...");
                Files.lines(Paths.get(inputFile)).forEach(line -> {
                    try {
                        Files.write(Paths.get(outputFile), (line + ";\n").getBytes(), StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        System.out.println("Can't write in the output file, error: " + e.getMessage());
                    }
                });
            } catch (IOException e) {
                throw new SemicolonusException("An IO Error occurred, error: " + e.getMessage());
            }

        }
    }
}
