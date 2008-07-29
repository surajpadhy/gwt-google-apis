/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.maps.sample.maps.client;

import com.google.gwt.maps.client.MapPane;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.MapTypeControl;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.LatLngBounds;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class CustomOverlayDemo extends MapsDemo {

  private MapWidget map;

  private boolean firstTime = true;

  public static MapsDemoInfo init() {
    return new MapsDemoInfo() {
      public MapsDemo createInstance() {
        return new CustomOverlayDemo();
      }

      public String getName() {
        return "Custom Overlays";
      }
    };
  }

  private static class RectangleOverlay extends Overlay {

    private final LatLngBounds bounds;

    private final Rectangle rectangle;

    private final int weight;

    private MapWidget map;

    private MapPane pane;

    public RectangleOverlay(LatLngBounds bounds, int weight) {
      this.bounds = bounds;
      this.weight = weight;
      rectangle = new Rectangle();
      rectangle.setBorderWidth(weight + "px");
      rectangle.setBorderColor("#888888");
    }

    protected Overlay copy() {
      return new RectangleOverlay(bounds, weight);
    }

    protected void initialize(MapWidget map) {
      this.map = map;
      pane = map.getPane(MapPane.MAP_PANE);
      pane.add(rectangle);
    }

    protected void redraw(boolean force) {
      // Only set the rectangle's size if the map's size has changed
      if (!force) {
        return;
      }

      Point sw = map.convertLatLngToDivPixel(bounds.getSouthWest());
      Point ne = map.convertLatLngToDivPixel(bounds.getNorthEast());
      pane.setWidgetPosition(rectangle,
          Math.min(sw.getX(), ne.getX()),
          Math.min(sw.getY(), ne.getY()));

      int width = Math.abs(ne.getX() - sw.getX()) - weight;
      int height = Math.abs(ne.getY() - sw.getY()) - weight;
      rectangle.setSize(width + "px", height + "px");
    }

    protected void remove() {
      rectangle.removeFromParent();
    }
  }

  private static class Rectangle extends Widget {
    public Rectangle() {
      setElement(DOM.createDiv());
      DOM.setStyleAttribute(getElement(), "borderStyle", "solid");
    }

    public void setBorderWidth(String width) {
      DOM.setStyleAttribute(getElement(), "borderWidth", width);
    }

    public void setBorderColor(String color) {
      DOM.setStyleAttribute(getElement(), "borderColor", color);
    }
  }

  public CustomOverlayDemo() {
    map = new MapWidget(new LatLng(37.4419, -122.1419), 13);
    map.setSize("500px", "300px");
    map.addControl(new SmallMapControl());
    map.addControl(new MapTypeControl());
    initWidget(map);
  }

  public void onShow() {
    // The map's bounds are meaningless until the map has been added to the DOM
    // and sized appropriately
    if (firstTime) {
      firstTime = false;
      LatLngBounds bounds = map.getBounds();
      LatLng southWest = bounds.getSouthWest();
      LatLng northEast = bounds.getNorthEast();
      double lngDelta = (northEast.getLongitude() - southWest.getLongitude()) / 4;
      double latDelta = (northEast.getLatitude() - southWest.getLatitude()) / 4;

      // generate bounds that covers center map with half the width and height
      LatLngBounds rectBounds = new LatLngBounds(
          new LatLng(southWest.getLatitude() + latDelta, southWest.getLongitude()
              + lngDelta),
          new LatLng(northEast.getLatitude() - latDelta, northEast.getLongitude()
              - lngDelta));

      map.addOverlay(new RectangleOverlay(rectBounds, 2));
    }
  }
}