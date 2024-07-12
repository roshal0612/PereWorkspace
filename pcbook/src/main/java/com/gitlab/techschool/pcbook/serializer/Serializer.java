package com.gitlab.techschool.pcbook.serializer;

import com.pcbook.pb.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Serializer {
    public void WriteBinaryFile(Laptop laptop, String filename) throws IOException {
        FileOutputStream fout = new FileOutputStream(filename);
        laptop.writeTo(fout);
        fout.close();
    }

    public Laptop ReadBinaryFile(String filename) throws IOException {
        FileInputStream fin = new FileInputStream(filename);
        Laptop laptop = Laptop.parseFrom(fin);
        fin.close();
        return laptop;
    }
}
