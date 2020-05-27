<template>
  <el-container style="margin-top: 5px;">
    <el-main>
      <el-tabs v-model="activeName" type="border-card">
        <el-tab-pane label="订单记录" name="list" :lazy="true">
          <order-list></order-list>
        </el-tab-pane>
        <el-tab-pane label="创建旧产品订单" name="add-old" :lazy="true">
          <order-add-old></order-add-old>
        </el-tab-pane>
        <el-tab-pane label="创建新产品订单" name="add-new" :lazy="true">
          <order-add-new></order-add-new>
        </el-tab-pane>
        <el-tab-pane label="回收站" name="dustbin" :lazy="true">
          <order-dustbin></order-dustbin>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
  import OrderList from "@/components/product/OrderList.vue";
  import OrderAddOld from "@/components/product/OrderAddOld.vue";
  import OrderAddNew from "@/components/product/OrderAddNew.vue";
  import OrderDustbin from "@/components/product/OrderDustbin.vue";

  export default {
    name: "Order",
    data() {
      return {
        activeName: "list"
      };
    },
    components: {
      "order-list": OrderList,
      "order-add-old": OrderAddOld,
      "order-add-new": OrderAddNew,
      "order-dustbin": OrderDustbin
    },
    methods: {

    }
  };
</script>

<style>
</style>
