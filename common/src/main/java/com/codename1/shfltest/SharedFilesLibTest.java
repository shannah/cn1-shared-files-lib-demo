/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Codename One through http://www.codenameone.com/ if you
 * need additional information or have any questions.
 */
package com.codename1.shfltest;

import static com.codename1.ui.CN.*;
import com.codename1.system.Lifecycle;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.io.*;
import com.codename1.ui.plaf.*;
import com.codename1.ui.util.Resources;
import com.codenameone.ext.io.sharedfiles.NotSupportedException;
import com.codenameone.ext.io.sharedfiles.SharedFile;
import com.codenameone.ext.io.sharedfiles.SharedFileManager;

import java.io.IOException;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose
 * of building native mobile applications using Java.
 */
public class SharedFilesLibTest extends Lifecycle {
    @Override
    public void runApp() {
        Form hi = new Form("Hi World", BoxLayout.y());
        Button openDirectory = new Button("Open Directory");
        openDirectory.addActionListener(evt -> {
            try {
                openDirectory();
            } catch (NotSupportedException ex) {
                Dialog.show("Not supported", ex.getMessage());
            }
        });

        Button openTextFile = new Button("Open Text File");
        openTextFile.addActionListener(evt -> {
            try {
                openTextFile();
            } catch (NotSupportedException ex) {
                Dialog.show("Not supported", ex.getMessage());
            }
        });

        Button openAnyFile = new Button("Open Any File");
        openTextFile.addActionListener(evt -> {
            try {
                openAnyFile();
            } catch (NotSupportedException ex) {
                Dialog.show("Not supported", ex.getMessage());
            }
        });

        Button openBookmarks = new Button("Open Bookmarks");
        openBookmarks.addActionListener(evt-> {
            try {
                openBookmarks();
            } catch (NotSupportedException ex) {
                Dialog.show("Not supported", ex.getMessage());
            }
        });

        hi.addAll(openDirectory, openTextFile, openAnyFile, openBookmarks);
        hi.show();
    }

    private void openBookmarks() {
        new BookmarksListForm().show();
    }

    private void openTextFile() {
        SharedFileManager.getInstance().openFile("text/plain").ready(sharedFile -> {
            new FileDetailsForm(sharedFile).show();
        });
    }

    private void openAnyFile() {
        SharedFileManager.getInstance().openFile().ready(sharedFile -> {
            new FileDetailsForm(sharedFile).show();
        });
    }

    private void openDirectory() {
        SharedFileManager.getInstance().openDirectory().ready(sharedFile -> {
            new FileListForm(sharedFile).show();
        });
    }

    private void printDirectory(SharedFile sharedFile) throws IOException {
        Log.p("Directory: " + sharedFile.getPath());
        Log.p("Files: " + sharedFile.listFiles().length);
        for (SharedFile child : sharedFile.listFiles()) {
            printFile(child);
        }
    }

    private void printFile(SharedFile file) throws IOException {
        if (file.isDir()) {
            printDirectory(file);
            return;
        }

        Log.p("File: " + file.getPath());
        Log.p("Size: " + file.getSize());
        Log.p("Contents:\n---\n");
        Log.p(Util.readToString(file.openInputStream()));
        Log.p("------\n");
    }

}
