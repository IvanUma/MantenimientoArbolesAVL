package avl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 08/07/13
 */
public class AvlTreeTest {

    AvlTree<Integer> avlTree;
    Comparator<?> comparator;

    @BeforeEach
    public void setUp() {
        comparator = Comparator.comparingInt((Integer o) -> o);
        avlTree = new AvlTree(comparator);
    }

    @AfterEach
    public void tearDown() {
        avlTree = null;
        comparator = null;
    }

    @Test
    @DisplayName("AVL tree is empty before anything is inserted")
    public void avlTreeIsEmpty() {
        assertTrue("AVL tree is empty", avlTree.avlIsEmpty());
    }

    @Test
    @DisplayName("AVL tree is not empty when an element is inserted")
    public void avlTreeIsNotEmptyAfterInsert() {
        avlTree.insert(5);

        assertFalse("AVL tree is not empty", avlTree.avlIsEmpty());
    }

    @Test
    @DisplayName("Element is on top when inserted on an AVL Tree")
    public void testInsertTop() {
        Integer value = 4;
        AvlNode<Integer> topNode = new AvlNode<>(value);
        String tree = " | 4";

        avlTree.insert(value);

        assertEquals(topNode, avlTree.getTop());
        assertEquals(tree, avlTree.toString());
    }

    @ParameterizedTest
    @DisplayName("Nodes with smaller value are smaller")
    @CsvSource({
            "4, 5",
            "2, 2",
            "6, 1"
    })
    public void smallerNodesHaveSmallerNumbers(int n1, int n2) {
        AvlNode<Integer> node1 = new AvlNode<Integer>(n1);
        AvlNode<Integer> node2 = new AvlNode<Integer>(n2);
        int expectedValue = Integer.compare(n1, n2);
        int actualValue = avlTree.compareNodes(node1, node2);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("Inserting one item on the left and on the right after inserting top")
    public void insertingRightAndLeftElementsJustAfterTop() {
        avlTree.insert(6);
        avlTree.insert(4);
        avlTree.insert(9);

        int expectedTopItem = 6;
        int actualTopItem = avlTree.getTop().getItem();

        int expectedLeftItem = 4;
        int actualLeftItem = avlTree.getTop().getLeft().getItem();

        int expectedRightItem = 9;
        int actualRightItem = avlTree.getTop().getRight().getItem();

        assertAll(
                () -> assertEquals(expectedTopItem, actualTopItem),
                () -> assertEquals(expectedLeftItem, actualLeftItem),
                () -> assertEquals(expectedRightItem, actualRightItem)
        );
    }

    @Test
    @DisplayName("Inserting left item after top")
    public void insertingLeftElementAfterTop() {
        int expectedTopItem = 6;
        int expectedLeftItem = 4;
        int actualTopItem, actualLeftItem;
        String tree = " | 6 | 4";

        avlTree.insert(6);
        avlTree.insert(4);

        actualTopItem = avlTree.getTop().getItem();
        actualLeftItem = avlTree.getTop().getLeft().getItem();

        assertAll(
                () -> assertEquals(expectedTopItem, actualTopItem),
                () -> assertEquals(expectedLeftItem, actualLeftItem),
                () -> assertEquals(tree, avlTree.toString())
        );
    }

    @Test
    @DisplayName("Parent node after inserting at right of top node is top node")
    public void testInsertingRightElement(){
        avlTree.insert(6);
        avlTree.insert(9);

        String expectedTree = " | 6 | 9";
        String actualTree = avlTree.toString();

        int expectedParentItem = 6;
        int actualParentItem = avlTree.getTop().getRight().getParent().getItem();

        int expectedRightItem = 9;
        int actualRightItem = avlTree.getTop().getItem();

        assertAll(
                () -> assertEquals(expectedTree, actualTree),
                () -> assertEquals(expectedParentItem, actualParentItem),
                () -> assertEquals(expectedRightItem, actualRightItem)
        );
    }

    /**
     * Test adding 7 - 4 - 9 - 3 - 5
     *
     */
    @Test
    @DisplayName("")
    public void testHeightAndBalanceOfASimpleBalancedTree() {
        AvlNode<Integer> topNode, leftNode, rightNode;
        int expectedHeightTop, expectedBalanceTop, actualBalanceTop, actualHeightTop;
        int expectedHeightLeft, expectedBalanceLeft, actualBalanceLeft, actualHeightLeft;
        int expectedHeightRight, expectedBalanceRight, actualBalanceRight, actualHeight;

        avlTree.insert(7);

        topNode = avlTree.getTop();

        
        expectedHeightTop = 0;
        expectedBalanceTop = 0;
        actualHeightTop = topNode.getHeight();
        actualBalanceTop = avlTree.getBalance(topNode);

        assertEquals(expectedBalanceTop, actualBalanceTop);
        assertEquals(expectedHeightTop, actualHeightTop);

        avlTree.insert(4);
        topNode = avlTree.getTop();
        leftNode = topNode.getLeft();

        expectedHeightTop = 1;
        expectedBalanceTop = -1;
        actualHeightTop = topNode.getHeight();
        actualBalanceTop = avlTree.getBalance(topNode);

        expectedBalanceLeft = 0;
        expectedHeightLeft = 0;
        actualHeightLeft = leftNode.getHeight();
        actualBalanceLeft = avlTree.getBalance(leftNode);

        //Por terminar

        assertEquals(expectedHeightTop, actualHeightTop);
        assertEquals(expectedBalanceTop, actualBalanceTop);

        avlTree.insert(9);
        topNode = avlTree.getTop();
        leftNode = topNode.getLeft();
        rightNode = topNode.getRight();

        assertEquals("testHeightOfASimpleBalancedTree", 0, node3.getHeight());
        assertEquals("testHeightOfASimpleBalancedTree", 1, node1.getHeight());
        assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node1));
        assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node3));

        String tree = " | 7 | 4 | 9";
        assertEquals("testHeightOfASimpleBalancedTree", tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 4 - 3
     *
     * @throws Exception
     */
    @Test
    public void testInsertingLeftLeftNodeAndRebalance() throws Exception {
        AvlNode<Integer> node1, node2, node3, node4, node5;

        node1 = new AvlNode<Integer>(7);
        avlTree.insertAvlNode(node1);
        assertEquals("testInsertingLeftLeftNodeAndRebalance", 0, node1.getHeight());
        assertEquals(0, avlTree.getBalance(node1));

        node2 = new AvlNode<Integer>(4);
        avlTree.insertAvlNode(node2);
        assertEquals("testInsertingLeftLeftNodeAndRebalance", 0, node2.getHeight());
        assertEquals("testInsertingLeftLeftNodeAndRebalance", 1, node1.getHeight());
        assertEquals("testInsertingLeftLeftNodeAndRebalance", -1, avlTree.getBalance(node1));
        assertEquals("testInsertingLeftLeftNodeAndRebalance", 0, avlTree.getBalance(node2));

        node3 = new AvlNode<Integer>(3);
        avlTree.insertAvlNode(node3);
        assertEquals("testInsertingLeftLeftNodeAndRebalance", node2, avlTree.getTop());
        assertEquals("testInsertingLeftLeftNodeAndRebalance", node3, node2.getLeft());
        assertEquals("testInsertingLeftLeftNodeAndRebalance", node1, node2.getRight());

        assertEquals("testInsertingLeftLeftNodeAndRebalance", 1, avlTree.getTop().getHeight());
        assertEquals("testInsertingLeftLeftNodeAndRebalance", 0,
                avlTree.getTop().getLeft().getHeight());
        assertEquals("testInsertingLeftLeftNodeAndRebalance", 0,
                avlTree.getTop().getRight().getHeight());
        assertEquals("testInsertingLeftLeftNodeAndRebalance", -1, avlTree.height(node1.getLeft()));
        assertEquals("testInsertingLeftLeftNodeAndRebalance", -1, avlTree.height(node1.getRight()));
        assertEquals("testInsertingLeftLeftNodeAndRebalance", -1, avlTree.height(node3.getLeft()));
        assertEquals("testInsertingLeftLeftNodeAndRebalance", -1, avlTree.height(node3.getRight()));

        String tree = " | 4 | 3 | 7";
        assertEquals("testInsertingLeftLeftNodeAndRebalance", tree, avlTree.toString());
    }

    @Test
    @DisplayName("Height is one after inserting on the right")
    public void heihtOneAfterInsertingRight(){
        avlTree.insert(7);
        avlTree.insert(10);

        int expectedTopHeight = 1;
        int actualTopHeight = avlTree.getTop().getHeight();

        int expectedRightHeight = 0;
        int actualRightHeight = avlTree.getTop().getRight().getHeight();

        assertAll(
                () -> assertEquals(expectedTopHeight, actualTopHeight),
                () -> assertEquals(expectedRightHeight, actualRightHeight)
        );
    }

    @Test
    @DisplayName("Height is one after inserting on the left")
    public void heightOneAfterInsertingLeft(){
        avlTree.insert(7);
        avlTree.insert(5);

        int expectedTopHeight = 1;
        int actualTopHeight = avlTree.getTop().getHeight();

        int expectedLeftHeight = 0;
        int actualLeftHeight = avlTree.getTop().getLeft().getHeight();

        assertAll(
                () -> assertEquals(expectedTopHeight, actualTopHeight),
                () -> assertEquals(expectedLeftHeight, actualLeftHeight)
        );
    }

    /**
     * Testing adding 7 - 4 - 3 - 2 - 1
     */
    @Test
    @DisplayName("Inserting 7 4 3 2 1")
    public void inserting7_4_3_2_1(){
        avlTree.insert(7);
        avlTree.insert(4);
        avlTree.insert(3);
        avlTree.insert(2);
        avlTree.insert(1);

        String expectedTree = " | 4 | 2 | 1 | 3 | 7";
        String actualTree = avlTree.toString();

        assertEquals(expectedTree, actualTree);
    }

    /**
     * Testing adding 7 - 4 - 3 - 2 - 1
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Inserting 7 8 9 10 11")
    public void inserting7_8_9_10_11() throws Exception {
        avlTree.insert(7);
        avlTree.insert(8);
        avlTree.insert(9);
        avlTree.insert(10);
        avlTree.insert(11);

        String expectedTree = " | 8 | 7 | 10 | 9 | 11";
        String actualTree = avlTree.toString();

        assertEquals(expectedTree, actualTree);
    }

    /**
     * Testing adding 7 - 2 - 3
     *
     * @throws Exception
     */
    @Test
    public void testInsertingLeftRightNodeAndRebalance() throws Exception {
        AvlNode<Integer> node1, node2, node3;

        node1 = new AvlNode<Integer>(7);
        avlTree.insertAvlNode(node1);

        node2 = new AvlNode<Integer>(2);
        avlTree.insertAvlNode(node2);

        node3 = new AvlNode<Integer>(3);
        avlTree.insertAvlNode(node3);

        assertEquals("testInsertingLeftRightNodeAndRebalance", node3, avlTree.getTop());
        assertEquals("testInsertingLeftRightNodeAndRebalance", node2, node3.getLeft());
        assertEquals("testInsertingLeftRightNodeAndRebalance", node1, node3.getRight());

        assertEquals("testInsertingLeftRightNodeAndRebalance", 1, avlTree.getTop().getHeight());
        assertEquals("testInsertingLeftRightNodeAndRebalance", 0,
                avlTree.getTop().getLeft().getHeight());
        assertEquals("testInsertingLeftRightNodeAndRebalance", 0,
                avlTree.getTop().getRight().getHeight());
        assertEquals("testInsertingLeftRightNodeAndRebalance", -1, avlTree.height(node2.getLeft()));
        assertEquals("testInsertingLeftRightNodeAndRebalance", -1, avlTree.height(node2.getRight()));
        assertEquals("testInsertingLeftRightNodeAndRebalance", -1, avlTree.height(node1.getLeft()));
        assertEquals("testInsertingLeftRightNodeAndRebalance", -1, avlTree.height(node1.getRight()));

        String tree = " | 3 | 2 | 7";
        assertEquals("testInsertingLeftRightNodeAndRebalance", tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 9 - 8
     *
     * @throws Exception
     */
    @Test
    public void testInsertingRightLeftNodeAndRebalance() throws Exception {
        AvlNode<Integer> node1, node2, node3;

        node1 = new AvlNode<Integer>(7);
        avlTree.insertAvlNode(node1);

        node2 = new AvlNode<Integer>(9);
        avlTree.insertAvlNode(node2);

        node3 = new AvlNode<Integer>(8);
        avlTree.insertAvlNode(node3);

        assertEquals("testInsertingRightLeftNodeAndRebalance", node3, avlTree.getTop());
        assertEquals("testInsertingRightLeftNodeAndRebalance", node1, node3.getLeft());
        assertEquals("testInsertingRightLeftNodeAndRebalance", node2, node3.getRight());

        assertEquals("testInsertingRightLeftNodeAndRebalance", 1, avlTree.getTop().getHeight());
        assertEquals("testInsertingRightLeftNodeAndRebalance", 0,
                avlTree.getTop().getLeft().getHeight());
        assertEquals("testInsertingRightLeftNodeAndRebalance", 0,
                avlTree.getTop().getRight().getHeight());
        assertEquals("testInsertingRightLeftNodeAndRebalance", -1, avlTree.height(node2.getLeft()));
        assertEquals("testInsertingRightLeftNodeAndRebalance", -1, avlTree.height(node2.getRight()));
        assertEquals("testInsertingRightLeftNodeAndRebalance", -1, avlTree.height(node1.getLeft()));
        assertEquals("testInsertingRightLeftNodeAndRebalance", -1, avlTree.height(node1.getRight()));

        String tree = " | 8 | 7 | 9";
        assertEquals("testInsertingRightLeftNodeAndRebalance", tree, avlTree.toString());
    }

    @Test
    @DisplayName("Search on empty tree")
    public void searchOnEmptyTree(){
        AvlNode<Integer> expectedNode = null;
        AvlNode<Integer> actualNode =  avlTree.search(8);

        assertEquals(expectedNode, actualNode);
    }

    @Test
    @DisplayName("Search top node")
    public void searchTopNode(){
        avlTree.insert(8);

        AvlNode<Integer> expectedNode = null;
        AvlNode<Integer> actualNode =  avlTree.search(8);

        assertEquals(expectedNode, actualNode);
    }

    @Test
    @DisplayName("Search right node")
    public void searchTopNode(){
        avlTree.insert(4);
        avlTree.insert(8);

        AvlNode<Integer> expectedNode = null;
        AvlNode<Integer> actualNode =  avlTree.search(8);

        int expectedParent = 4;
        int actualParent = actualNode.getParent().getItem();

        assertAll(
                () -> assertEquals(expectedNode, actualNode),
                () -> assertEquals(expectedParent, actualParent)
        );
    }

    @Test
    public void testDeletingLeafNodes() throws Exception {
        AvlNode<Integer> node1, node2, node3, node4, node5;

        node1 = new AvlNode<Integer>(7);
        avlTree.insertAvlNode(node1);

        node2 = new AvlNode<Integer>(9);
        avlTree.insertAvlNode(node2);

        node3 = new AvlNode<Integer>(2);
        avlTree.insertAvlNode(node3);

        node4 = new AvlNode<Integer>(8);
        avlTree.insertAvlNode(node4);

        node5 = new AvlNode<Integer>(3);
        avlTree.insertAvlNode(node5);

        String tree = " | 7 | 2 | 3 | 9 | 8";
        assertEquals("testDeletingLeafNodes", tree, avlTree.toString());

        avlTree.delete(3); // right leaf node
        assertEquals("testDeletingLeafNodes", null, node3.getRight());
        assertEquals("testDeletingLeafNodes", 0, node3.getHeight());
        assertEquals("testDeletingLeafNodes", 2, avlTree.getTop().getHeight());
        assertEquals("testDeletingLeafNodes", " | 7 | 2 | 9 | 8", avlTree.toString());

        avlTree.delete(8); // left leaf node
        assertEquals("testDeletingLeafNodes", null, node2.getLeft());
        assertEquals("testDeletingLeafNodes", 0, node2.getHeight());
        assertEquals("testDeletingLeafNodes", 1, avlTree.getTop().getHeight());
        assertEquals("testDeletingLeafNodes", " | 7 | 2 | 9", avlTree.toString());

        avlTree.delete(2); // left leaf node
        assertEquals("testDeletingLeafNodes", null, node1.getLeft());
        assertEquals("testDeletingLeafNodes", 1, node1.getHeight());
        assertEquals("testDeletingLeafNodes", " | 7 | 9", avlTree.toString());

        avlTree.delete(9); // right leaf node
        assertEquals("testDeletingLeafNodes", null, node1.getRight());
        assertEquals("testDeletingLeafNodes", 0, node1.getHeight());
        assertEquals("testDeletingLeafNodes", " | 7", avlTree.toString());

        avlTree.delete(7); // left leaf node
        assertEquals("testDeletingLeafNodes", null, avlTree.getTop());
        assertEquals("testDeletingLeafNodes", "", avlTree.toString());
    }




    @Nested()
    @DisplayName("")
    public class testWith5ElementsTree{
        private final int[] elements = {7, 9, 2, 8, 3};

        @BeforeEach
        public void setUp(){
            for(int item : elements){
                avlTree.insert(item);
            }
        }

        @Test
        @DisplayName("String of the tree is expected to be in order")
        public void stringOfTreeIsInOrder(){
            String expected = " | 7 | 2 | 3 | 9 | 8";
            assertEquals(expected, avlTree.toString());
        }

        @Test
        public void testDeletingNodesWithOneLeaf(){
            int deleteItem2 = 2;


            avlTree.delete(deleteItem2);
            assertEquals(node3.getItem(), node1.getLeft().getItem());
            assertEquals(null, node3.getRight());
            assertEquals(0, node3.getHeight());
            assertEquals(2, avlTree.getTop().getHeight());
            assertEquals(" | 7 | 3 | 9 | 8", avlTree.toString());


            avlTree.delete(9);
            assertEquals(node2.getItem(), node1.getRight().getItem());
            assertEquals(null, node2.getLeft());
            assertEquals(0, node2.getHeight());
            assertEquals(1, avlTree.getTop().getHeight());
            assertEquals(" | 7 | 3 | 8", avlTree.toString());
        }
    }


    @Nested()
    @DisplayName("Test with 8 elements tree")
    public class testWith8ElementsTree{

        private final int[] elements = {20, 8, 22, 4, 12, 24, 10, 14};

        @BeforeEach
        public void setUp(){
            for(int item : elements){
                avlTree.insert(item);
            }
        }

        @Test
        @DisplayName("Expected height for a 8 elements tree is 3")
        public void heightIsCorrectFor8ElementsTree(){
            int expectedHeight = 3;
            assertEquals(expectedHeight, avlTree.getTop().getHeight());
        }

        @Test
        @DisplayName("String of the tree is expected to be in order")
        public void stringOfTreeIsInOrder(){
            String expected = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
            assertEquals(expected, avlTree.toString());
        }

        @Test
        public void testFindSuccessor(){
            int expectedSearchItem10 = 10;
            int expectedSearchItem12 = 12;
            int expectedSearchItem20 = 20;
            AvlNode<Integer> searchingForItem;


            searchingForItem = avlTree.search(8);
            assertEquals(expectedSearchItem10, avlTree.findSuccessor(searchingForItem).getItem());

            searchingForItem = avlTree.search(10);
            assertEquals(expectedSearchItem12, avlTree.findSuccessor(searchingForItem).getItem());

            searchingForItem = avlTree.search(14);
            assertEquals(expectedSearchItem20, avlTree.findSuccessor(searchingForItem).getItem());

        }

        @Test
        @DisplayName("Deleting two nodes with leaves makes the rebalancing correct")
        public void deletingNodesWithTwoLeaves() {
            int firstDeleteItem  = 12;
            int secondDeleteItem = 8;
            AvlNode<Integer> searchingForItem = avlTree.search(8);

            avlTree.delete(firstDeleteItem);

            assertEquals(4, (int) searchingForItem.getRight().getItem());
            assertEquals(" | 20 | 8 | 4 | 14 | 10 | 22 | 24", avlTree.toString());

            avlTree.delete(secondDeleteItem);

            assertEquals(10, (int) avlTree.getTop().getLeft().getItem());
            assertEquals(" | 20 | 10 | 4 | 14 | 22 | 24", avlTree.toString());
        }

        @Test
        @DisplayName("Deleting the deep leaf node and rebalancing")
        public void deletingDeepLeafNodeAndRebalancing(){
            int deleteItem = 22;
            int expectedItem = 12;
            AvlNode<Integer> expectedFirstItemSearch = avlTree.search(8);
            AvlNode<Integer> expectedSecondItemSearch = avlTree.search(20);

            avlTree.delete(deleteItem);

            assertEquals(expectedItem, (int) avlTree.getTop().getItem());
            assertEquals(expectedFirstItemSearch, avlTree.getTop().getLeft());
            assertEquals(expectedSecondItemSearch, avlTree.getTop().getRight());
        }

        @Test
        @DisplayName("Deleting the top node deletes the first inserted item")
        public void deletingTopNodeDeletesFirstInsertedItem() {
            int firstItem = elements[0];

            avlTree.delete(firstItem);
            assertEquals(" | 12 | 8 | 4 | 10 | 22 | 14 | 24", avlTree.toString());
        }


    }
}
