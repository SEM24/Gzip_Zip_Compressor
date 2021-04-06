package com.company;

import java.io.IOException;
import java.io.OutputStream;
@FunctionalInterface
public interface ICompressionStrategy {
    OutputStream compress(OutputStream data) throws IOException;
}
