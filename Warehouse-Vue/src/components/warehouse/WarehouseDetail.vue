<template>
  <el-container>
    <el-card style="width: 800px; margin-top: 15px">
      <div slot="header">
        <el-page-header @back="goBack" content="详情页面">
        </el-page-header>
      </div>
      <div>
        <el-input width="400px" v-model="info.id" :disabled="true">
          <template slot="prepend">仓库编号</template>
        </el-input>
      </div>
      <div style="margin-top: 15px;">
        <el-input width="400px" v-model="info.name" :disabled="true">
          <template slot="prepend">仓库名称</template>
        </el-input>
      </div>
      <el-divider>
        <el-tag effect="dark">仓库负责人</el-tag>
      </el-divider>
      <el-tooltip placement="top" v-for="operator in info.operators" :key="operator.name">
        <div slot="content">
          工号：{{operator.id}}<br/>
          姓名：{{operator.name}}<br/>
          性别：{{operator.gender}}<br/>
          电话号码：{{operator.phone}}
        </div>
        <el-tag>{{operator.name}}</el-tag>
      </el-tooltip>
      <el-divider>
        <el-tag type="success" effect="dark">仓库存储清单</el-tag>
      </el-divider>
      <el-table :data="productList" v-loading="loading" border stripe>
        <el-table-column prop="id" label="产品编号" sortable width="150"></el-table-column>
        <el-table-column prop="name" label="产品名"></el-table-column>
        <el-table-column prop="supplier" label="供应商"></el-table-column>
        <el-table-column prop="total" label="数量" sortable></el-table-column>
      </el-table>
    </el-card>
  </el-container>
</template>

<script>
  export default {
    name: "WarehouseDetail",
    data: function () {
      return {
        info: {},
        productList: [],
        loading: false
      }
    },

    mounted() {
      this.info = this.$route.params;
      this.loadProductList("/warehouse/" + this.info.id + "/products");
    },

    methods: {
      goBack() {
        this.$router.replace("/home/warehouse");
      },

      loadProductList(url) {
        this.loading = true;
        var that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            console.log(res);
            that.productList = res.data.productList;
            that.loading = false;
            that.$message.success("数据加载成功");
          } else {
            that.loading = false;
            that.$message.error("数据加载失败");
          }
        }).catch(res => {
          console.log(res);
          that.loading = false;
          that.$message.error("服务器异常");
        });
      },

      onPageChange(val) {
        this.productLoading = true;
        let url = "/warehouse/" + this.info.id + "/products?page=" + val;
        this.loadProductList(url);
      },
    }
  }
</script>

<style>

</style>