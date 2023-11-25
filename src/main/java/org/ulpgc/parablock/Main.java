package org.ulpgc.parablock;

import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.operators.multipliers.distributed.DistributeMultiplicationClient;
import org.ulpgc.parablock.operators.multipliers.distributed.DistributedMultiplicationOrchestrator;

public class Main {
    public static void main(String[] args) {
        DistributeMultiplicationClient client = new DistributeMultiplicationClient();
        client.start();

        DistributedMultiplicationOrchestrator distributedMultiplicationOrchestrator = new DistributedMultiplicationOrchestrator();
        distributedMultiplicationOrchestrator.multiply(new DenseMatrix(10,new double[10][10]), new DenseMatrix(10,new double[10][10]));
    }
}
