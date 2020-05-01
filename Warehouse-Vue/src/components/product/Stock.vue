<template>
  <el-container style="margin-top: 5px;">
    <el-main>
      <el-tabs v-model="activeName" type="border-card">
        <el-tab-pane label="入库记录" name="input">
          <stock-input/>
        </el-tab-pane>
        <el-tab-pane label="出库记录" name="output">
          <stock-output/>
        </el-tab-pane>
        <el-tab-pane label="添加新记录" name="add">
          <stock-add/>
        </el-tab-pane>
        <el-tab-pane label="回收站" name="dustbin">
          <stock-dustbin/>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
import StockInput from "@/components/product/StockInput.vue";
import StockOutput from "@/components/product/StockOutput.vue";
import StockAdd from "@/components/product/StockAdd.vue";
import StockDustbin from "@/components/product/StockDustbin.vue";

export default {
  name: "Stock",
  data() {
    return {
      activeName: "input"
    };
  },

  mounted() {
    
  },

  components: {
    "stock-input": StockInput,
    "stock-output": StockOutput,
    "stock-add": StockAdd,
    "stock-dustbin": StockDustbin
  },
  methods: {
    
  }
};
</script>

<style>
</style>
