<template>
  <el-container>
    <el-header style="margin-top: 20px">
      <el-row :gutter="12">
        <el-col :span="8">
          <el-input v-model="keyword" placeholder="通过产品名搜索" prefix-icon="el-icon-search"></el-input>
        </el-col>
        <el-col :span="3">
          <el-button @click="searchProduct()" type="primary" icon="el-icon-search">查询</el-button>
        </el-col>
      </el-row>
    </el-header>

    <el-table :data="productList" v-loading="loading" border stripe>
      <el-table-column prop="id" label="产品编号" sortable></el-table-column>
      <el-table-column prop="name" label="产品名"></el-table-column>
      <el-table-column prop="supplier" label="供货商"></el-table-column>
      <el-table-column prop="total" label="商品总量" sortable></el-table-column>
    </el-table>

    <el-footer>
      <el-pagination
        layout="total, sizes, prev, pager, next, jumper"
        background
        style="margin-top: 10px;"
        :total="total"
        :current-page="page"
        :page-size="pageSize"
        @current-change="onPageChange"
        @size-change="onPageSizeChange"
        :page-sizes="[5, 10, 15, 20, 25]"
      ></el-pagination>
    </el-footer>
  </el-container>
</template>

<script>
  export default {
    name: "Inventory",
    data() {
      return {
        productList: [],
        total: 0,     // 查询到的产品总数
        page: 1,      // 当前页码
        pageSize: 10, // 当前页面大小
        keyword: "",
        loading: true
      }
    },

    mounted() {
      this.loadProductList("/products");
    },

    methods: {
      loadProductList(url) {
        this.loading = true;
        let that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            console.log(res);
            that.productList = res.data.productList;
            that.total = res.data.total;
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

      searchProduct() {
        if (this.keyword.trim() === "") {
          this.$message.warning("请输入关键字");
          return;
        }
        this.loading = true;
        let url = "/products?page=" + this.page + "&pageSize=" + this.pageSize + "&keyword=" + this.keyword.trim();
        this.loadProductList(url);
      },

      onPageChange(val) {
        this.page = val;
        this.loading = true;
        let url = "/products?page=" + this.page + "&pageSize=" + this.pageSize;
        if (this.keyword !== "") {
          url += "&keyword=" + this.keyword.trim();
        }
        this.loadProductList(url);
      },

      onPageSizeChange(val) {
        this.pageSize = val;
        if (this.pageSize * (this.page - 1) >= this.total) {
          this.page = 1;
          this.pageSize = 10;
        }
        this.loading = true;
        let url = "/products?page=" + this.page + "&pageSize=" + this.pageSize;
        if (this.keyword !== "") {
          url += "&keyword=" + this.keyword.trim();
        }
        this.loadProductList(url);
      },
    }
  };
</script>

<style>
</style>
