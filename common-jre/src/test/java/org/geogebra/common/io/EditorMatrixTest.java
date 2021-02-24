package org.geogebra.common.io;

import org.geogebra.common.AppCommonFactory;
import org.geogebra.common.jre.headless.AppCommon;
import org.junit.Before;
import org.junit.Test;

import com.himamis.retex.editor.share.event.KeyEvent;
import com.himamis.retex.editor.share.util.JavaKeyCodes;

public class EditorMatrixTest {
	private static final String matix3x3 = "{{1,2,3}, {4,5,6}, {7,8,9}}";
	private static EditorChecker checker;
	private static AppCommon app = AppCommonFactory.create();

	@Before
	public void setUp() {
		checker = new EditorChecker(app);
	}

	@Test
	public void testCaretInitialPosition() {
		checker.matrixFromParser(matix3x3).checkPath(0, 0, 0);
	}

	@Test
	public void testCaretShouldStayInsideOnHomeInitially() {
		checker.matrixFromParser(matix3x3).typeKey(JavaKeyCodes.VK_HOME).checkPath(0, 0, 0);
	}

	@Test
	public void testCaretShouldStayInsideOnHome() {
		checker.matrixFromParser(matix3x3)
				.right(6)
				.typeKey(JavaKeyCodes.VK_HOME).checkPath(0, 0, 0);
	}

	@Test
	public void testCaretShouldStayInsideOnEnd() {
		checker.matrixFromParser(matix3x3).typeKey(JavaKeyCodes.VK_END).checkPath(1, 8, 0);
	}

	@Test
	public void testCaretShouldStayInsideOnLeftArrow() {
		checker.matrixFromParser(matix3x3)
			.right(6)
			.left(20).checkPath(0, 0, 0);
	}

	@Test
	public void testCaretShouldStayInsideOnRightArrow() {
		checker.matrixFromParser(matix3x3).right(20).checkPath(1, 8, 0);
	}

	@Test
	public void testCaretShouldStayInsideOnUpArrow() {
		checker.matrixFromParser(matix3x3)
				.repeatKey(JavaKeyCodes.VK_DOWN, 2)
				.repeatKey(JavaKeyCodes.VK_UP, 20).checkPath(0, 0, 0);
	}

	@Test
	public void testCaretShouldStayInsideOnDownArrow() {
		checker.matrixFromParser(matix3x3).repeatKey(JavaKeyCodes.VK_DOWN, 20).checkPath(0, 6, 0);
	}

	@Test
	public void testSelectionShouldDelete00Only() {
		checker.matrixFromParser(matix3x3).setModifiers(KeyEvent.SHIFT_MASK)
				.repeatKey(JavaKeyCodes.VK_RIGHT, 2)
				.typeKey(JavaKeyCodes.VK_DELETE)
				.checkAsciiMath("{{,2,3},{4,5,6},{7,8,9}}");
	}

	@Test
	public void testSelectionShouldDelete01Only() {
		checker.matrixFromParser(matix3x3)
				.repeatKey(JavaKeyCodes.VK_RIGHT, 1)
				.setModifiers(KeyEvent.SHIFT_MASK)
				.repeatKey(JavaKeyCodes.VK_RIGHT, 2)
				.typeKey(JavaKeyCodes.VK_DELETE)
				.checkAsciiMath("{{1,,3},{4,5,6},{7,8,9}}");
	}

	@Test
	public void testSelectionShouldDelete11Only() {
		checker.matrixFromParser(matix3x3)
				.repeatKey(JavaKeyCodes.VK_RIGHT, 2)
				.repeatKey(JavaKeyCodes.VK_DOWN, 1)
				.setModifiers(KeyEvent.SHIFT_MASK)
				.repeatKey(JavaKeyCodes.VK_RIGHT, 1)
				.typeKey(JavaKeyCodes.VK_DELETE)
				.checkAsciiMath("{{1,2,3},{4,,6},{7,8,9}}");
	}

	@Test
	public void testSelectionShouldDelete02Only() {
		checker.matrixFromParser(matix3x3)
				.repeatKey(JavaKeyCodes.VK_RIGHT, 4)
				.setModifiers(KeyEvent.SHIFT_MASK)
				.repeatKey(JavaKeyCodes.VK_RIGHT, 1)
				.typeKey(JavaKeyCodes.VK_DELETE)
				.checkAsciiMath("{{1,2,},{4,5,6},{7,8,9}}");
	}
}
