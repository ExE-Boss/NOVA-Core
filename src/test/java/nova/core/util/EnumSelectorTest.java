/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nova.core.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import static nova.testutils.NovaAssertions.assertThat;

/**
 *
 * @author ExE Boss
 */
public class EnumSelectorTest {

	EnumSelector <EnumExample> enumSelectorExample1;
	EnumSelector <EnumExample> enumSelectorExample2;
	EnumSelector <EnumExample> enumSelectorExample3;
	EnumSelector <EnumExample> enumSelectorExample4;

	public EnumSelectorTest() {
	}

    @Before
    public void setUp() {
		enumSelectorExample1 = EnumSelector.of(EnumExample.class).blockAll()
				.apart(EnumExample.EXAMPLE_24).apart(EnumExample.EXAMPLE_42).lock();

		enumSelectorExample2 = EnumSelector.of(EnumExample.class).allowAll()
				.apart(EnumExample.EXAMPLE_24).apart(EnumExample.EXAMPLE_42).lock();

		enumSelectorExample3 = EnumSelector.of(EnumExample.class).allowAll().lock();
		enumSelectorExample4 = EnumSelector.of(EnumExample.class).blockAll().lock();
    }

	@Test
	public void test1Locked() {
		boolean result = enumSelectorExample1.locked();
		assertThat(result).isEqualTo(true);
	}

	@Test
	public void test1Disallows_EXAMPLE_8() {
		boolean result = enumSelectorExample1.allows(EnumExample.EXAMPLE_8);
		assertThat(result).isEqualTo(false);
	}

	@Test
	public void test1Disallows_EXAMPLE_16() {
		boolean result = enumSelectorExample1.allows(EnumExample.EXAMPLE_16);
		assertThat(result).isEqualTo(false);
	}

	@Test
	public void test1Allows_EXAMPLE_24() {
		boolean result = enumSelectorExample1.allows(EnumExample.EXAMPLE_24);
		assertThat(result).isEqualTo(true);
	}

	@Test
	public void test1Disallows_EXAMPLE_32() {
		boolean result = enumSelectorExample1.allows(EnumExample.EXAMPLE_16);
		assertThat(result).isEqualTo(false);
	}

	@Test
	public void test1Allows_EXAMPLE_42() {
		boolean result = enumSelectorExample1.allows(EnumExample.EXAMPLE_42);
		assertThat(result).isEqualTo(true);
	}

	@Test
	public void test1Disallows_EXAMPLE_48() {
		boolean result = enumSelectorExample1.allows(EnumExample.EXAMPLE_48);
		assertThat(result).isEqualTo(false);
	}

	@Test
	public void test1Disallows_EXAMPLE_64() {
		boolean result = enumSelectorExample1.allows(EnumExample.EXAMPLE_64);
		assertThat(result).isEqualTo(false);
	}

	@Test
	public void test2Locked() {
		boolean result = enumSelectorExample2.locked();
		assertThat(result).isEqualTo(true);
	}

	@Test
	public void test2Allows_EXAMPLE_8() {
		boolean result = enumSelectorExample2.allows(EnumExample.EXAMPLE_8);
		assertThat(result).isEqualTo(true);
	}

	@Test
	public void test2Allows_EXAMPLE_16() {
		boolean result = enumSelectorExample2.allows(EnumExample.EXAMPLE_16);
		assertThat(result).isEqualTo(true);
	}

	@Test
	public void test2Disallows_EXAMPLE_24() {
		boolean result = enumSelectorExample2.allows(EnumExample.EXAMPLE_24);
		assertThat(result).isEqualTo(false);
	}

	@Test
	public void test2Allows_EXAMPLE_32() {
		boolean result = enumSelectorExample2.allows(EnumExample.EXAMPLE_16);
		assertThat(result).isEqualTo(true);
	}

	@Test
	public void test2Disallows_EXAMPLE_42() {
		boolean result = enumSelectorExample2.allows(EnumExample.EXAMPLE_42);
		assertThat(result).isEqualTo(false);
	}

	@Test
	public void test2Allows_EXAMPLE_48() {
		boolean result = enumSelectorExample2.allows(EnumExample.EXAMPLE_48);
		assertThat(result).isEqualTo(true);
	}

	@Test
	public void test2Allows_EXAMPLE_64() {
		boolean result = enumSelectorExample2.allows(EnumExample.EXAMPLE_64);
		assertThat(result).isEqualTo(true);
	}

	@Test
	public void test3AllowsAll() {
		boolean result = enumSelectorExample3.allowsAll();
		assertThat(result).isEqualTo(true);
	}

	@Test
	public void test3NotBlocksAll() {
		boolean result = enumSelectorExample3.blocksAll();
		assertThat(result).isEqualTo(false);
	}

	@Test
	public void test4NotAllowsAll() {
		boolean result = enumSelectorExample4.allowsAll();
		assertThat(result).isEqualTo(false);
	}

	@Test
	public void test4BlocksAll() {
		boolean result = enumSelectorExample4.blocksAll();
		assertThat(result).isEqualTo(true);
	}

	@Test
	public void test1StreamSize() {
		long result = enumSelectorExample1.stream().count();
		assertThat(result).isEqualTo(2);
	}

	@Test
	public void test2StreamSize() {
		long result = enumSelectorExample2.stream().count();
		assertThat(result).isEqualTo(EnumExample.values().length - 2);
	}

	@Test
	public void test3StreamSize() {
		long result = enumSelectorExample3.stream().count();
		assertThat(result).isEqualTo(EnumExample.values().length);
	}

	@Test
	public void test4StreamSize() {
		long result = enumSelectorExample4.stream().count();
		assertThat(result).isEqualTo(0);
	}

	public static enum EnumExample {
		EXAMPLE_8,
		EXAMPLE_16,
		EXAMPLE_24,
		EXAMPLE_32,
		EXAMPLE_48,
		EXAMPLE_64,

		EXAMPLE_42;
	}
}