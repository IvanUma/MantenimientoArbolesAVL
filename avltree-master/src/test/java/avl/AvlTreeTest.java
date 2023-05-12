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
        assertTrue(avlTree.avlIsEmpty(), "AVL tree is empty");
    }

    @Nested
    @DisplayName("avlIsEmpty method")
    class isEmpty{
        @Test
        @DisplayName("AVL tree is not empty when an element is inserted")
        public void avlTreeIsNotEmptyAfterInsert() {
            avlTree.insert(5);

            assertFalse(avlTree.avlIsEmpty(), "AVL tree is not empty");
        }

        @Test
        @DisplayName("Element is on top when inserted on an AVL Tree")
        public void testInsertTop() {
            Integer expectedItem = 4;
            String tree = " | 4";

            avlTree.insert(expectedItem);

            assertEquals(expectedItem, avlTree.getTop().getItem());
            assertEquals(tree, avlTree.toString());
        }
    }

    @Nested
    @DisplayName("height and balance methods")
    class heightAndBalance{
        @Test
        @DisplayName("Height and balance on empty tree is 0")
        public void emptyTreeHeightIsZero(){
            assertAll(
                    () -> assertThrows(NullPointerException.class, () -> avlTree.getHeight()),
                    () -> assertThrows(NullPointerException.class, () -> avlTree.getBalance(avlTree.getTop()))
            );
        }

        @Test
        @DisplayName("Height and balance is one after inserting on the right")
        public void heihtOneAfterInsertingRight(){
            avlTree.insert(7);
            avlTree.insert(10);

            int expectedTopHeight = 1;
            int actualTopHeight = avlTree.getTop().getHeight();

            int expectedRightHeight = 0;
            int actualRightHeight = avlTree.getTop().getRight().getHeight();

            int expectedBalance = 1;
            int actualBalance = avlTree.getBalance(avlTree.getTop());

            assertAll(
                    () -> assertEquals(expectedTopHeight, actualTopHeight),
                    () -> assertEquals(expectedRightHeight, actualRightHeight),
                    () -> assertEquals(expectedBalance, actualBalance)
            );
        }

        @Test
        @DisplayName("Height is one and balance minus one after inserting on the left")
        public void heightOneAfterInsertingLeft(){
            avlTree.insert(7);
            avlTree.insert(5);

            int expectedTopHeight = 1;
            int actualTopHeight = avlTree.getTop().getHeight();

            int expectedLeftHeight = 0;
            int actualLeftHeight = avlTree.getTop().getLeft().getHeight();

            int expectedBalance = -1;
            int actualBalance = avlTree.getBalance(avlTree.getTop());

            assertAll(
                    () -> assertEquals(expectedTopHeight, actualTopHeight),
                    () -> assertEquals(expectedLeftHeight, actualLeftHeight),
                    () -> assertEquals(expectedBalance, actualBalance)
            );
        }

        @Test
        @DisplayName("Height one and balance zero after inserting on both sides")
        public void heightOneAndBalanceZeroAfterInsertingBothSides(){
            avlTree.insert(7);
            avlTree.insert(5);
            avlTree.insert(9);

            int expectedTopHeight = 1;
            int actualTopHeight = avlTree.getTop().getHeight();

            int expectedLeftHeight = 0;
            int actualLeftHeight = avlTree.getTop().getLeft().getHeight();

            int expectedRightHeight = 0;
            int actualRightHeight = avlTree.getTop().getRight().getHeight();

            int expectedBalance = 0;
            int actualBalance = avlTree.getBalance(avlTree.getTop());

            assertAll(
                    () -> assertEquals(expectedTopHeight, actualTopHeight),
                    () -> assertEquals(expectedLeftHeight, actualLeftHeight),
                    () -> assertEquals(expectedRightHeight, actualRightHeight),
                    () -> assertEquals(expectedBalance, actualBalance)
            );
        }
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


    @Nested
    @DisplayName("Inserting leaf nodes")
    public class insertingLeafNodes{
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
        public void insertingRightElement(){
            avlTree.insert(6);
            avlTree.insert(9);

            String expectedTree = " | 6 | 9";
            String actualTree = avlTree.toString();

            int expectedParentItem = 6;
            int actualParentItem = avlTree.getTop().getRight().getParent().getItem();

            int expectedRightItem = 9;
            int actualRightItem = avlTree.getTop().getRight().getItem();

            assertAll(
                    () -> assertEquals(expectedTree, actualTree),
                    () -> assertEquals(expectedParentItem, actualParentItem),
                    () -> assertEquals(expectedRightItem, actualRightItem)
            );
        }

/*
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
*/


    /*
    @Test
    public void testInsertingLeftRightNodeAndRebalance() {
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


    @Test
    public void testInsertingRightLeftNodeAndRebalance() {
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
    }*/
    }

    @Nested
    @DisplayName("Searching leaf nodes")
    public class searchingLeafNodes{
        @Test
        @DisplayName("Search on empty tree")
        public void searchOnEmptyTree(){
            AvlNode<Integer> expectedNode = null;
            AvlNode<Integer> actualNode =  avlTree.search(8);

            assertEquals(expectedNode, actualNode);
        }

        @Test
        @DisplayName("Search top node on one element tree returns that one")
        public void searchTopNodeOnOneElementTreeReturnsThatElement(){
            avlTree.insert(8);

            int expectedNode = 8;
            int actualNode =  avlTree.search(8).getItem();

            assertEquals(expectedNode, actualNode);
        }

        @Test
        @DisplayName("Search right node")
        public void searchRightNode(){
            avlTree.insert(4);
            avlTree.insert(8);

            AvlNode<Integer> node = avlTree.search(8);

            int expectedNode = 8;
            int actualNode =  node.getItem();

            int expectedParent = 4;
            int actualParent = node.getParent().getItem();

            assertAll(
                    () -> assertEquals(expectedNode, actualNode),
                    () -> assertEquals(expectedParent, actualParent)
            );
        }

        @Test
        @DisplayName("Search left node")
        public void searchLeftNode(){
            avlTree.insert(4);
            avlTree.insert(2);

            AvlNode<Integer> node = avlTree.search(2);

            int expectedNode = 2;
            int actualNode =  node.getItem();

            int expectedParent = 4;
            int actualParent = node.getParent().getItem();

            assertAll(
                    () -> assertEquals(expectedNode, actualNode),
                    () -> assertEquals(expectedParent, actualParent)
            );
        }
    }


    @Nested
    @DisplayName("Deleting leaf nodes")
    public class deleteLeafNodes{

        final int[] elements = {7,9,2};

        @BeforeEach
        public void setUp(){
            for(int item: elements){
                avlTree.insert(item);
            }
        }

        @Test
        @DisplayName("Deleting left leaf node")
        public void deletingLeafLeafNode(){
            avlTree.delete(2);

            int expectedHeight = 1;
            int actualHeight = avlTree.getHeight();

            int expectedRight = 9;
            int actualRight = avlTree.getTop().getRight().getItem();

            assertAll(
                    () -> assertNull(avlTree.getTop().getLeft()),
                    () -> assertEquals(expectedHeight, actualHeight),
                    () -> assertEquals(expectedRight, actualRight)
            );
        }


        @Test
        @DisplayName("Deleting right leaf node")
        public void deletingRightLeafNode() {
            avlTree.delete(9);

            int expectedHeight = 1;
            int actualHeight = avlTree.getHeight();

            int expectedLeft = 2;
            int actualLeft = avlTree.getTop().getLeft().getItem();

            assertAll(
                    () -> assertNull(avlTree.search(9)),
                    () -> assertNull(avlTree.getTop().getRight()),
                    () -> assertEquals(expectedHeight, actualHeight),
                    () -> assertEquals(expectedLeft, actualLeft)
            );
        }

        @Test
        @DisplayName("Deleting both leaf nodes")
        public void deletingBothLeafNodes(){
            avlTree.delete(9);
            avlTree.delete(2);

            int expectedHeight = 0;
            int actualHeight = avlTree.getHeight();

            assertAll(
                    () -> assertNull(avlTree.getTop().getLeft()),
                    () -> assertNull(avlTree.getTop().getRight()),
                    () -> assertEquals(expectedHeight, actualHeight)
            );
        }

        @Test
        @DisplayName("Deleting complete tree")
        public void deletingCompleteTree(){
            for(int item: elements){
                avlTree.delete(item);
            }


            assertAll(
                    () -> assertNull(avlTree.getTop()),
                    () -> assertThrows(NullPointerException.class, () -> avlTree.getHeight())
            );

        }
    }


    @Nested
    @DisplayName("Tests with a 5 elements tree")
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
        @DisplayName("Deleting nodes with one leaf")
        public void deletingNodesWithOneLeaf(){
            avlTree.delete(3);


            int expectedItemLeftOfNode7 = 2;
            int actualItemLeftOfNode7 = avlTree.getTop().getLeft().getItem();

            int expectedHeightOfNode2 = 0;
            int actualHeightOfNode2 = avlTree.getTop().getLeft().getHeight();

            int expectedHeighOfNode7WithOut3 = 2;
            int actualHeightOfNode7WithOut3 = avlTree.getHeight();

            String expectedStringWithOut3 = " | 7 | 2 | 9 | 8";
            String actualStringWithOut3 = avlTree.toString();

             assertAll(
                    () -> assertEquals(expectedItemLeftOfNode7, actualItemLeftOfNode7),
                    () -> assertNull(avlTree.getTop().getLeft().getRight()),
                    () -> assertEquals(expectedHeightOfNode2, actualHeightOfNode2),
                    () -> assertEquals(expectedHeighOfNode7WithOut3, actualHeightOfNode7WithOut3),
                    () -> assertEquals(expectedStringWithOut3, actualStringWithOut3)
            );



           avlTree.delete(8);

            int expectedItemRightOfNode7 = 9;
            int actualItemRightOfNode7= avlTree.getTop().getRight().getItem();

            int expectedHeightOfNode9 = 0;
            int actualHeightOfNode9 = avlTree.getTop().getRight().getHeight();

            int expectedHeighOfNode7WithOut8 = 1;
            int actualHeightOfNode7WithOut8 = avlTree.getTop().getHeight();

            String expectedStringWithOut8 = " | 7 | 2 | 9";
            String actualStringWithOut8 = avlTree.toString();

            assertAll(
                    () ->  assertEquals(expectedItemRightOfNode7, actualItemRightOfNode7),
                    () ->  assertNull(avlTree.getTop().getRight().getLeft()),
                    () -> assertEquals(expectedHeightOfNode9, actualHeightOfNode9),
                    () ->assertEquals(expectedHeighOfNode7WithOut8, actualHeightOfNode7WithOut8),
                    () ->assertEquals(expectedStringWithOut8, actualStringWithOut8)
            );


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

            assertEquals(14, (int) searchingForItem.getRight().getItem());
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
