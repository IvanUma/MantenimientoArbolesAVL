package avl;


import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 09/07/13 Time: 15:29
 */
public class AvlNodeTest {

  private AvlNode<Integer> node;

  @BeforeEach
  public void setUp(){
    node = new AvlNode<Integer>(5);
  }

  @AfterEach
  public void tearDown() throws Exception {
    node = null;
  }

  @Test
  @DisplayName("node with left node has left")
  public void hasLeft() {
    AvlNode<Integer> node2 = new AvlNode<Integer>(4);
    node.setLeft(node2);
    assertTrue(node.hasLeft());
  }

  @Test
  @DisplayName("node with right node has right")
  public void hasRight() {
    AvlNode<Integer> node2 = new AvlNode<Integer>(6);
    node.setRight(node2);
    assertTrue(node.hasRight());
  }

  @Test
  @DisplayName("using setHeight should change he height")
  public void shouldSetHeight() {
    int expectedHeight = 1000213;
    node.setHeight(expectedHeight);
    int actualHeight = node.getHeight();

    assertEquals(expectedHeight, actualHeight);
  }
}
