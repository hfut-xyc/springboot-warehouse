<template>
  <el-container>
    <el-header class="header">
      <el-row :gutter="12">
        <el-col :span="8">
          <el-input v-model="keyword" placeholder="搜索仓库" prefix-icon="el-icon-search"></el-input>
        </el-col>
        <el-col :span="3">
          <el-button @click="searchWarehouse()" type="primary" icon="el-icon-search">查询</el-button>
        </el-col>
        <!-- <el-col :span="3">
          <el-button @click="isDialogVisible=true" type="success" icon="el-icon-plus">添加仓库</el-button>
        </el-col> -->
      </el-row>
    </el-header>

    <el-table :data="warehouseList" v-loading="loading" border stripe>
      <el-table-column prop="id" label="仓库ID" sortable width="100"></el-table-column>
      <el-table-column prop="name" label="仓库名" width="300">
        <template slot-scope="scope">
          <span style="color: #0083ee;cursor: pointer" @click="goToDetail(scope.row)"
                title="点击查看详情">{{ scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="operators" label="管理人员">
        <template slot-scope="scope">
          <el-tooltip placement="top" v-for="operator in scope.row.operators" :key="operator.name">
            <div slot="content">工号：{{operator.id}}<br/>姓名：{{operator.name}}<br/>性别：{{operator.gender}}<br/>电话号码：{{operator.phone}}
            </div>
            <el-tag>
              {{operator.name}}
            </el-tag>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>

    <el-footer>
      <el-pagination
        layout="total, sizes, prev, pager, next, jumper"
        background
        style="margin-top: 10px;"
        @size-change="onPageSizeChange"
        @current-change="onPageChange"
        :page-sizes="[5, 10, 15, 20, 25]"
        :page-size="pageSize"
        :current-page="page"
        :total="total"
      ></el-pagination>
    </el-footer>
  </el-container>
</template>

<script>
  export default {
    name: "Warehouse",
    data: function () {
      return {
        total: 0, // 查询到的用户总数
        page: 1, // 当前页码
        pageSize: 10, // 当前页面大小
        keyword: "", // 查询仓库名的关键字
        warehouseList: [], // 获得的查询结果
        loading: false // 页面表格是否处于加载状态
      };
    },
    mounted: function () {
      this.loading = true;
      this.loadWarehouseList("/warehouses");
    },

    methods: {
      goToDetail(row) {
        this.$router.push({name: '仓库详情', params: row});
      },

      loadWarehouseList(url) {
        var that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            console.log(res);
            that.warehouseList = res.data.warehouseList;
            that.total = res.data.total;
            that.loading = false;
            that.$message.success("数据加载成功");
          } else {
            that.loading = false;
            that.$message.error("数据加载失败");
          }
        })
          .catch(res => {
            console.log(res);
            that.loading = false;
            that.$message.error("服务器异常");
          });
      },

      searchWarehouse() {
        if (this.keyword.trim() === "") {
          this.$message.warning("请输入关键字");
          return;
        }
        this.loading = true;
        var url = "/warehouses?page=" + this.page + "&pageSize=" + this.pageSize + "&keyword=" + this.keyword.trim();
        this.loadWarehouseList(url);
      },

      onPageChange(val) {
        this.page = val;
        this.loading = true;
        var url = "/warehouses?page=" + this.page + "&pageSize=" + this.pageSize;
        if (this.keyword !== "") {
          url += "&keyword=" + this.keyword.trim();
        }
        this.loadWarehouseList(url);
      },

      onPageSizeChange(val) {
        this.pageSize = val;
        if (this.pageSize * (this.page - 1) >= this.total) {
          this.page = 1;
          this.pageSize = 10;
        }
        this.loading = true;
        var url = "/warehouses?page=" + this.page + "&pageSize=" + this.pageSize;
        if (this.keyword !== "") {
          url += "&keyword=" + this.keyword.trim();
        }
        this.loadWarehouseList(url);
      },
    }
  };
</script>

<style>
  .el-tag + .el-tag {
    margin-left: 10px;
  }

  .detail-form label {
    width: 100px;
    color: #99a9bf;
    text-align: end;
    margin-right: 20px;
  }

  .el-form-item {
    margin-bottom: 10px;
  }
</style>
