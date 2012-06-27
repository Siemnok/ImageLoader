/**
 * Copyright 2012 Novoda Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.novoda.imageloader.core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import android.content.Context;
import android.view.Display;

public class ImageTagFactoryTest {
    
    private ImageTagFactory imageTagFactory;
    private int height = 12;
    private int width = 9;
    private int defaultResourceId = 1;
    private String url = "google.com";
    
    @Before
    public void beforeEachTest() {
        imageTagFactory = new ImageTagFactory(9, 12, 1);        
    }
    
    @Test
    public void shouldSetNormalPropertiesOnTheImageTag() {
        ImageTag imageTag = imageTagFactory.build(url);
        
        assertEquals(defaultResourceId, imageTag.getLoadingResourceId());
        assertEquals(defaultResourceId, imageTag.getNotFoundResourceId());
        assertEquals(height, imageTag.getHeight());
        assertEquals(width, imageTag.getWidth());
        assertEquals(url, imageTag.getUrl());
    }
    
    @Test
    public void shouldSetPreviewProperties() {
        int previewHeight = 1;
        int previewWidth = 2;
        imageTagFactory.usePreviewImage(previewWidth, previewHeight, true);
        ImageTag imageTag = imageTagFactory.build(url);
        
        assertEquals(previewHeight, imageTag.getPreviewHeight());
        assertEquals(previewWidth, imageTag.getPreviewWidth());
    }
    
    @Test
    public void shouldUseTheSameUrlForPreview() {
        imageTagFactory.usePreviewImage(1, 1, true);
        ImageTag imageTag = imageTagFactory.build(url);
        
        assertEquals(url, imageTag.getPreviewUrl());
    }
    
    @Test
    public void shouldNotUseTheSameUrlForPreview() {
        imageTagFactory.usePreviewImage(1, 1, false);
        ImageTag imageTag = imageTagFactory.build(url);
        
        assertNull(imageTag.getPreviewUrl());
    }
    
    @Test
    public void shouldUseDisplaySizes() {
        final Display display = mock(Display.class);
        when(display.getHeight()).thenReturn(21);
        when(display.getWidth()).thenReturn(12);
        imageTagFactory = new ImageTagFactory(null, 1) {
            @Override
            protected Display prepareDisplay(Context context) {
                return display;
            }
        };
        ImageTag imageTag = imageTagFactory.build(url);
        assertEquals(21, imageTag.getHeight());
        assertEquals(12, imageTag.getWidth());
    }

}
