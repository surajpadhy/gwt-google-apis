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
package com.google.gwt.ajaxsearch.client;

import com.google.gwt.ajaxsearch.client.impl.GblogSearch;

/**
 * A Google blog search.
 */
public class BlogSearch extends Search {
  private static final GblogSearch IMPL = GblogSearch.IMPL;

  public BlogSearch() {
    super(IMPL);
  }

  public void setResultOrder(ResultOrder order) {
    IMPL.setResultOrder(this, order.getValue());
  }

  public void setSiteRestriction(String site) {
    IMPL.setSiteRestriction(this, site);
  }
}
