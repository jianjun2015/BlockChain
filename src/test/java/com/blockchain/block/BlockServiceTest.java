package com.blockchain.block;

import com.alibaba.fastjson.JSON;
import com.blockchain.model.Block;
import com.blockchain.model.Transaction;
import com.blockchain.security.CryptoUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangjianjun
 * @description:
 * @date: 2018/3/21 18:31
 * @version: V1.0
 */
public class BlockServiceTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testBlockMine() throws Exception {
        //创建一个空的区块链
        List<Block> blockchain = new ArrayList<>();
        //生成创世区块
        Block block = new Block(1, System.currentTimeMillis(), new ArrayList<Transaction>(), 1, "1", "1");
        //加入创世区块到区块链里
        blockchain.add(block);
        System.out.println(JSON.toJSONString(blockchain));

        //创建一个空的交易结合
        List<Transaction> txs = new ArrayList<>();
        Transaction tx1 = new Transaction();
        Transaction tx2 = new Transaction();
        Transaction tx3 = new Transaction();
        txs.add(tx1);
        txs.add(tx2);
        txs.add(tx3);
        //加入系统奖励的交易
        Transaction sysTx = new Transaction();
        txs.add(sysTx);
        //获取当前区块链里的最后一个区块
        Block latestBlock = blockchain.get(blockchain.size() - 1);

        int nonce = 1;
        String hash = "";
        while(true){
            hash = CryptoUtil.SHA256(latestBlock.getHash() + JSON.toJSONString(txs) + nonce);
            if (hash.startsWith("0000")) {
                System.out.println("=====计算结果正确，计算次数为：" +nonce+ ",hash:" + hash);
                break;
            }
            nonce++;
            System.out.println("计算错误，hash:" + hash);
        }

        Block newBlock = new Block(latestBlock.getIndex() + 1, System.currentTimeMillis(), txs, nonce, latestBlock.getHash(), hash);
        blockchain.add(newBlock);
        System.out.println("挖矿后的区块链：" + JSON.toJSONString(blockchain));
    }
}
