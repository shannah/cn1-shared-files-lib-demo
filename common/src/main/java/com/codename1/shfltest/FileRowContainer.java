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

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.BorderLayout;
import com.codenameone.ext.io.sharedfiles.SharedFile;

public class FileRowContainer extends Container {
    public FileRowContainer(SharedFile child) {
        setLayout(new BorderLayout());
        Button button = new Button(child.getName());
        button.setMaterialIcon(child.isDir() ? FontImage.MATERIAL_FOLDER : FontImage.MATERIAL_FILE_OPEN);
        Button addBookmark = new Button(FontImage.MATERIAL_BOOKMARK_ADD);
        Button removeBookmark = new Button(FontImage.MATERIAL_BOOKMARK_REMOVE);

        add(BorderLayout.CENTER, button);
        if (child.isBookmarked()) {
            add(BorderLayout.EAST, removeBookmark);
        } else {
            add(BorderLayout.EAST, addBookmark);
        }

        button.addActionListener(evt->{
            if (child.isDir()) {
                new FileListForm(child).show();
            } else {
                new FileDetailsForm(child).show();
            }
        });

        addBookmark.addActionListener(evt->{
            SharedFile bookmarkedFile = child.createBookmark();
            FileRowContainer.this
                    .getParent()
                    .replace(FileRowContainer.this, new FileRowContainer(bookmarkedFile), CommonTransitions.createEmpty());
        });

        removeBookmark.addActionListener(evt -> {
            SharedFile unbookmarkedFile = child.deleteBookmark();
            FileRowContainer.this
                    .getParent()
                    .replace(FileRowContainer.this, new FileRowContainer(unbookmarkedFile), CommonTransitions.createEmpty());

        });
    }
}
