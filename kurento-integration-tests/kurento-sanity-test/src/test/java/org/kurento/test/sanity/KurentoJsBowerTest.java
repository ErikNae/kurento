/*
 * (C) Copyright 2014 Kurento (http://kurento.org/)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */

package org.kurento.test.sanity;

import static org.kurento.commons.PropertiesManager.getProperty;
import static org.kurento.test.config.TestConfiguration.BOWER_KURENTO_CLIENT_TAG_DEFAULT;
import static org.kurento.test.config.TestConfiguration.BOWER_KURENTO_CLIENT_TAG_PROP;
import static org.kurento.test.config.TestConfiguration.BOWER_KURENTO_UTILS_TAG_DEFAULT;
import static org.kurento.test.config.TestConfiguration.BOWER_KURENTO_UTILS_TAG_PROP;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kurento.test.utils.Shell;
import org.springframework.core.io.ClassPathResource;

/**
 * Sanity test for kurento-js releases with Bower.
 *
 * @author Boni Garcia (bgarcia@gsyc.es)
 * @since 4.2.5
 */
public class KurentoJsBowerTest extends KurentoJsBase {

  public KurentoJsBowerTest() {
    kurentoUrl = "./";
  }

  @BeforeClass
  public static void runBower() throws IOException {
    String bowerClientTag =
        getProperty(BOWER_KURENTO_CLIENT_TAG_PROP, BOWER_KURENTO_CLIENT_TAG_DEFAULT);
    String bowerUtilsTag =
        getProperty(BOWER_KURENTO_UTILS_TAG_PROP, BOWER_KURENTO_UTILS_TAG_DEFAULT);
    if (!bowerClientTag.isEmpty()) {
      bowerClientTag = "#" + bowerClientTag;
    }
    if (!bowerUtilsTag.isEmpty()) {
      bowerUtilsTag = "#" + bowerUtilsTag;
    }

    log.debug("Using bower to download kurento-client" + bowerClientTag + "\n" + Shell
        .runAndWait("sh", "-c", "bower install --allow-root kurento-client" + bowerClientTag));
    log.debug("Using bower to download kurento-utils" + bowerUtilsTag + "\n"
        + Shell.runAndWait("sh", "-c", "bower install --allow-root kurento-utils" + bowerUtilsTag));

    final String outputFolder =
        new ClassPathResource("static").getFile().getAbsolutePath() + File.separator;

    log.debug("Copying files from bower_components/kurento-utils/js to " + outputFolder
        + Shell.runAndWait("sh", "-c", "cp -r bower_components/kurento-utils/js " + outputFolder));
    log.debug("Copying files from bower_components/kurento-client/js to " + outputFolder
        + Shell.runAndWait("sh", "-c", "cp -r bower_components/kurento-client/js " + outputFolder));
  }

  @AfterClass
  public static void rmBower() {
    Shell.runAndWait("sh", "-c", "rm -rf bower_components");
  }

  @Test
  public void kurentoJsBowerTest() {
    doTest();
  }

}
