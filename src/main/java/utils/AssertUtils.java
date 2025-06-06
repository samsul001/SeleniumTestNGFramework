package utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class AssertUtils {
	
	//soft assertions
	
	private static final ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);
	
	public static SoftAssert getSoftAssert() {
		return softAssert.get();
	}
	
	public static void initSoftAssert() {
		softAssert.set(new SoftAssert());
	}
	
	public static void assertAll() {
		getSoftAssert().assertAll();
		softAssert.remove();
	}
	
	//================================Hard Assertion====================================================================
	//===============================Author : Samsul ===================================================================
	
	public static void assertEquals(String actual, String expected, String message) {
		Assert.assertEquals(actual, expected, message);
	}
	
	public static void assertEquals(int actual, int expected, String message) {
		Assert.assertEquals(actual, expected, message);
	}
	
	public static void assertEquals(boolean actual, boolean expected, String message) {
		Assert.assertEquals(actual, expected, message);
	}
	
	public static void assertEquals(char actual, char expected, String message) {
		Assert.assertEquals(actual, expected, message);
	}
	
	public static void asserEquals(Collection<String> actual, Collection<String> expected, String message) {
		Assert.assertEquals(actual, expected, message);
	}
	
	public static void asserEquals(Set<String> actual, Set<String> expected, String message) {
		Assert.assertEquals(actual, expected, message);
	}
	
	public static void assertTrue(boolean condition, String message) {
		Assert.assertTrue(condition, message);
	}
	
	public static void assertFalse(boolean condition, String message) {
		Assert.assertFalse(condition, message);
	}
	
	public static void assertContains(String actual, String expected, String message) {
		Assert.assertTrue(actual.contains(expected), message);
	}
	
	public static void assertNull(Object obj, String message) {
		Assert.assertNull(obj,message);
	}
	
	public static void assertNotNull(Object obj, String message) {
		Assert.assertNotNull(obj,message);
	}
	
	public static void assertListSize(List<?> list, int expectedSize, String message) {
		Assert.assertEquals(list.size(), expectedSize, message);
	}
	
	public static void assertBetweenCollection(Collection<?> actual, Collection<?> expected, String message) {
		Assert.assertEquals(actual, expected);
	}
	
	//==============================Soft Assrt utils=====================================================================
	//==============================Author: Samsul ======================================================================
	
	public static void softAssertEquals(String actual, String expected, String message) {
		getSoftAssert().assertEquals(actual, expected, message);
	}
	
	public static void softAssertEquals(int actual, int expected, String message) {
		getSoftAssert().assertEquals(actual, expected, message);
	}
	
	public static void softAssertEquals(boolean actual, boolean expected, String message) {
		getSoftAssert().assertEquals(actual, expected, message);
	}
	
	public static void softAssertTrue(boolean condition, String message) {
		getSoftAssert().assertTrue(condition, message);
	}
	
	public static void softAssertFalse(boolean condition, String message) {
		getSoftAssert().assertFalse(condition, message);
	}
	
	public static void softAssertContains(String actual, String expected, String message) {
		getSoftAssert().assertTrue(actual.contains(expected), message);
	}
	
	public static void softAssertNull(Object obj, String message) {
		getSoftAssert().assertNull(obj, message);
	}
	
	public static void softAssertNotNull(Object obj, String message) {
		getSoftAssert().assertNotNull(obj, message);
	}
	
	public static void softAssertListSize(List<String> list, int expected, String message) {
		getSoftAssert().assertEquals(list.size(), expected, message);
	}
	
	public static void softAssertBetweenCollection(Collection<?> actual, Collection<?> expected, String message) {
		getSoftAssert().assertEquals(actual, expected, message);
	}
	
}
