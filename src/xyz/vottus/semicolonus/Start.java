package xyz.vottus.semicolonus;

import xyz.vottus.semicolonus.app.SemicolonusProcessor;
import xyz.vottus.semicolonus.exception.SemicolonusException;
import xyz.vottus.semicolonus.utils.Utils;

/**
 * Semicolonus v1.0 by VottusCode
 * A little tool that appends semicolon on end of every line in an input file.
 *
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
public class Start {

    // if set to true, then:
    // finalArgs[0] = "input.txt"
    // finalArgs[1] = "output.txt"
    // finalArgs[2] = "--override"
    private static boolean isDev = true;

    public static void main(String[] args) {
        String[] finalArgs = isDev ? new String[]{"input.txt", "output.txt", "--override"} : args;
        boolean override = Utils.getOverride(finalArgs);

        try {
            new SemicolonusProcessor(finalArgs[0], finalArgs[1], override).process();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Correct usage of the app: java -jar Semicolonus.jar [input] [output] (--override | optional)");
            System.exit(0);
        } catch(SemicolonusException e) {
            System.out.println("An error has occured, error: " + e);
        }
    }
}
