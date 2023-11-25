package org.ulpgc.parablock;

import org.ulpgc.parablock.matrix.DenseMatrix;
import org.ulpgc.parablock.operators.multipliers.distributed.DistributeMultiplicationClient;
import org.ulpgc.parablock.operators.multipliers.distributed.DistributedMultiplicationOrchestrator;

public class Main {
    public static void main(String[] args) {
        DistributedMultiplicationOrchestrator distributedMultiplicationOrchestrator = new DistributedMultiplicationOrchestrator();
        DistributeMultiplicationClient client = new DistributeMultiplicationClient();
        client.start();

        distributedMultiplicationOrchestrator.multiply(new DenseMatrix(10,new double[10][10]), new DenseMatrix(10,new double[10][10]));
    }
}
