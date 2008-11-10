/*
 * Copyright 2008 Google Inc.
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
package com.google.gwt.visualization.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.visualization.client.events.Handler;
import com.google.gwt.visualization.client.events.PageHandler;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.events.SortHandler;

/**
 * 
 * Table visualization.
 * 
 * @see <a
 *      href="http://code.google.com/apis/visualization/documentation/gallery/table.html">
 *      Table Visualization Reference</a>
 */
public class Table extends Visualization<Table.DrawOptions> 
    implements Selectable {

  /**
   * Options for drawing the table visualization.
   * 
   */
  public static class DrawOptions extends AbstractDrawOptions {
    
    /**
     * A parameter passed to several of the setters.
     */
    public static enum Policy {
      DISABLE, ENABLE, EVENT;
      
      @Override
      public String toString() {
        switch (this) {
          case ENABLE:
            return "enable";
          case EVENT:
            return "event";
          case DISABLE:
            return "disable";
          default:
            // unreachable
            throw new RuntimeException();
        }
      }
    }
    
    public static final DrawOptions create() {
      return JavaScriptObject.createObject().cast();
    }

    protected DrawOptions() {
    }
    
    public final native void setAllowHtml(boolean allowHtml) /*-{
      this.allowHtml = allowHtml;
    }-*/;

    public final void setPage(Policy policy) {
      setPage(policy.toString());
    }
    
    public final native void setPageSize(int pageSize) /*-{
      this.pageSize = pageSize;
    }-*/;
    
    public final native void setShowRowNumber(boolean showRowNumber) /*-{
      this.showRowNumber = showRowNumber;
    }-*/;

    public final void setSort(Policy policy) {
      setSort(policy.toString());
    }
    
    private native void setPage(String page) /*-{
      this.page = page;
    }-*/;
    
    private native void setSort(String sort) /*-{
      this.sort = sort;
    }-*/;
  }

  public static native Table create(Element parent) /*-{
    return new $wnd.google.visualization.Table(parent);
  }-*/;

  protected Table() {
  }
  
  public final void addPageListener(PageHandler listener) {
    Handler.addHandler(this, "page", listener);
  }
  
  public final void addSelectHandler(SelectHandler handler) {
    SelectionHelper.addSelectHandler(this, handler);
  }

  public final void addSortListener(SortHandler listener) {
    Handler.addHandler(this, "sort", listener);
  }

  public final Selection getSelection() {
    return SelectionHelper.getSelection(this);
  }

  public final void setSelection(Selection sel) {
    SelectionHelper.setSelection(this, sel);
  }
}
