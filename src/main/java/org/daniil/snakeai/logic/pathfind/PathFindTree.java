package org.daniil.snakeai.logic.pathfind;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.daniil.snakeai.logic.snake.cell.Cell;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PathFindTree {

    private final Cell[][] gameArea;


    public PathFindTree(Cell[][] gameArea) {
        this.gameArea=gameArea;
    }


    public boolean pathExists(int fromX, int toX, int fromY, int toY) {

        VirtualCell[][] virtualPath = prepareVirtualCell();

        PriorityQueue<CellNode> cellNodes = new PriorityQueue<>(Comparator.comparingInt(CellNode::getDistance));

        CellNode cellNode = new CellNode(fromX,fromY,0,null,gameArea,null);

        cellNodes.add(cellNode);

        while (cellNodes.size()!=0) {

            cellNode = cellNodes.poll();

            if (cellNode.getX() == toX && cellNode.getY() == toY)
                return true;

            ArrayList<CellNode> children = cellNode.getChildren();

            for (CellNode child : children) {
                if (virtualPath[child.getY()][child.getX()].isVisited()) {
                    continue;
                }
                cellNodes.add(child);
                virtualPath[child.getY()][child.getX()].setVisited(true);
            }

        }
        return false;
    }

    public CellNode getPath(int fromX, int toX, int fromY, int toY) {

        VirtualCell[][] virtualPath = prepareVirtualCell();

        PriorityQueue<CellNode> cellNodes = new PriorityQueue<>(Comparator.comparingInt(CellNode::getDistance));

        CellNode cellNode = new CellNode(fromX,fromY,0,null,gameArea,null);

        cellNodes.add(cellNode);

        while (cellNodes.size()!=0) {

            cellNode = cellNodes.poll();

            if (cellNode.getX() == toX && cellNode.getY() == toY)
                return cellNode;

            ArrayList<CellNode> children = cellNode.getChildren();

            for (CellNode child : children) {
                if (virtualPath[child.getY()][child.getX()].isVisited()) {
                    continue;
                }
                cellNodes.add(child);
                virtualPath[child.getY()][child.getX()].setVisited(true);
            }

        }
        return null;
    }

    public CellNode getLongestPath(int fromX, int fromY) {

        VirtualCell[][] virtualPath = prepareVirtualCell();

        //PriorityQueue<CellNode> cellNodes = new PriorityQueue<>((o1, o2) -> o2.getDistance()-o1.getDistance());
        PriorityQueue<CellNode> cellNodes = new PriorityQueue<>(Comparator.comparingInt(CellNode::getDistance));

        CellNode cellNode = new CellNode(fromX,fromY,0,null,gameArea,null);

        cellNodes.add(cellNode);

        while (cellNodes.size()!=0) {

            cellNode = cellNodes.poll();

            ArrayList<CellNode> children = cellNode.getChildren();

            for (CellNode child : children) {
                if (virtualPath[child.getY()][child.getX()].isVisited()) {
                    continue;
                }
                cellNodes.add(child);
                virtualPath[child.getY()][child.getX()].setVisited(true);
            }

            if (cellNodes.size()==0) return cellNode;

        }
        return null;
    }

    private VirtualCell[][] prepareVirtualCell() {

        VirtualCell[][] target = new VirtualCell[gameArea.length][gameArea.length];

        for (Cell[] cells : gameArea) {
            for (Cell cell : cells) {
                target[cell.getY()][cell.getX()] = new VirtualCell(cell.getX(),cell.getY(),false);
            }
        }

        return target;
    }


    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    public static class VirtualCell {
        private final int x,y;
        private boolean visited;
    }

}
