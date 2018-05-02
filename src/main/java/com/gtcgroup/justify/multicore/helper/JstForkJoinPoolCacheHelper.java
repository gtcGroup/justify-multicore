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

package com.gtcgroup.justify.multicore.helper;

import java.util.concurrent.ForkJoinPool;

import com.gtcgroup.justify.core.po.JstExceptionPO;
import com.gtcgroup.justify.core.testing.exception.internal.JustifyTestingException;
import com.gtcgroup.justify.multicore.testing.extension.JstConfigureTestingMulticorePO;

/**
 * This Cache Helper class manages a {@link ForkJoinPool}.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2018 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author Marvin Toll
 * @since v8.5
 */
public enum JstForkJoinPoolCacheHelper {

	/** Instance */
	INSTANCE;

	private static ForkJoinPool forkJoinPool;

	public static void deleteForkJoinPoolForTesting() {
		forkJoinPool = null;
	}

	public static ForkJoinPool getForkJoinPool() {

		if (null == forkJoinPool) {
			getForkJoinPool(Runtime.getRuntime().availableProcessors() * 2);
		}
		return forkJoinPool;
	}

	public static void getForkJoinPool(final int parallelism) {

		forkJoinPool = new ForkJoinPool(parallelism);
	}

	/**
	 * @return {@link JstConfigureTestingMulticorePO}
	 */
	public static JstConfigureTestingMulticorePO initializeForkJoinPool(
			final Class<? extends JstConfigureTestingMulticorePO> configureTestingMulticoreClassPO) {

		JstConfigureTestingMulticorePO configureTestInstancePO = null;

		try {
			configureTestInstancePO = configureTestingMulticoreClassPO.newInstance();

		} catch (@SuppressWarnings("unused") final Exception e) {
			throw new JustifyTestingException(
					JstExceptionPO.withMessage("\n\t\tThe [" + JstConfigureTestingMulticorePO.class.getSimpleName()
							+ "] class should be extended with\n\t\tan instance ["
							+ configureTestingMulticoreClassPO.getSimpleName() + "] containing proper values."));
		}

		if (configureTestInstancePO.containsParallelism()) {
			getForkJoinPool(configureTestInstancePO.getParallelism());
		} else {
			getForkJoinPool();
		}

		return configureTestInstancePO;
	}
}