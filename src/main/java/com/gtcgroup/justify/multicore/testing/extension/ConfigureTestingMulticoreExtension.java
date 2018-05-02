/*
 * [Licensed per the Open Source "MIT License".]
 *
 * Copyright (c) 2006 - 2018 by
 * Global Technology Consulting Group, Inc. at
 * http://gtcGroup.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.gtcgroup.justify.multicore.testing.extension;

import java.util.concurrent.ForkJoinPool;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.gtcgroup.justify.core.testing.extension.JstBaseExtension;
import com.gtcgroup.justify.multicore.helper.JstForkJoinPoolCacheHelper;

/**
 * This {@link Extension} class initializes a {@link ForkJoinPool}.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2018 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author Marvin Toll
 * @since 8.5
 */
class ConfigureTestingMulticoreExtension extends JstBaseExtension implements BeforeAllCallback {

	@Override
	public void beforeAll(final ExtensionContext extensionContext) throws Exception {

		try {

			final Class<? extends JstConfigureTestingMulticorePO> configureTestingMulticoreClassPO = initializePropertiesFromAnnotation(
					extensionContext);

			JstForkJoinPoolCacheHelper.initializeForkJoinPool(configureTestingMulticoreClassPO);

		} catch (

		final RuntimeException e) {
			handleBeforeAllException(extensionContext, e); // Covered.
		}
	}

	@Override
	protected Class<? extends JstConfigureTestingMulticorePO> initializePropertiesFromAnnotation(
			final ExtensionContext extensionContext) {

		final JstConfigureTestingMulticore configureMulticore = (JstConfigureTestingMulticore) retrieveAnnotation(
				extensionContext.getRequiredTestClass(), JstConfigureTestingMulticore.class);

		return configureMulticore.configureTestingMulticorePO();
	}
}