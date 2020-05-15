<template>
  <el-container style="margin-top: 5px;">
    <el-main>
      <el-tabs v-model="activeName" type="border-card">
        <el-tab-pane label="入库订单" name="input">
          <order-input></order-input>
        </el-tab-pane>
        <el-tab-pane label="出库订单" name="output">
          <order-output></order-output>
        </el-tab-pane>
        <el-tab-pane label="添加新订单" name="add">
          <order-add></order-add>
        </el-tab-pane>
        <el-tab-pane label="回收站" name="dustbin">
          <order-dustbin></order-dustbin>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
import OrderInput from "@/components/product/OrderInput.vue";
import OrderOutput from "@/components/product/OrderOutput.vue";
import OrderAdd from "@/components/product/OrderAdd.vue";
import OrderDustbin from "@/components/product/OrderDustbin.vue";

export default {
  name: "Order",
  data() {
    return {
      activeName: "input"
    };
  },
  components: {
    "order-input": OrderInput,
    "order-output": OrderOutput,
    "order-add": OrderAdd,
    "order-dustbin": OrderDustbin
  },
  methods: {
    
  }
};
</script>

<style>
</style>
