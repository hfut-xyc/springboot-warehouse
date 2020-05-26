<template>
  <el-container>
    <el-header style="margin-top: 10px">
      <el-row :gutter="24">
        <el-col :span="9">
          <el-date-picker v-model="period" :editable="false"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间">
          </el-date-picker>
        </el-col>
        <el-col :span="8">
          <el-button @click="searchOrder()" type="primary" icon="el-icon-search">按创建时间查询</el-button>
        </el-col>
      </el-row>
    </el-header>

    <el-table :data="orderList" v-loading="loading" border stripe>
      <el-table-column prop="id"  label="订单编号" sortable></el-table-column>
      <el-table-column prop="eid" label="负责人编号" sortable></el-table-column>
      <el-table-column prop="wid" label="仓库编号" sortable></el-table-column>
      <el-table-column prop="pid" label="产品编号" sortable></el-table-column>
      <el-table-column prop="amount" label="订单数量" sortable></el-table-column>
      <el-table-column prop="createTime" label="创建时间" sortable width="180"></el-table-column>
      <el-table-column prop="updateTime" label="修改时间" sortable width="180"></el-table-column>
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="deleteOrder(scope.row)" size="mini" icon="el-icon-delete" type="danger" plain>删除</el-button>
        </template>
      </el-table-column>
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
    name: "OrderList",
    data() {
      return {
        orderList: [],
        total: 0,     // 查询到的订单总数
        page: 1,      // 当前页码
        pageSize: 10, // 当前页面大小
        period: [],
        loading: true
      }
    },
    mounted() {
      this.loadOrderList("/orders")
    },

    methods: {
      loadOrderList(url) {
        let that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            console.log(res);
            that.orderList = res.data.orderList;
            that.total = res.data.total;
            that.loading = false;
            that.$message.success("数据加载成功");
          } else {
            that.loading = false;
            that.$message.error("数据加载失败");
          }
        }).catch(err => {
          that.loading = false;
          that.$message.error("服务器异常");
        });
      },

      searchOrder() {
        if (this.period.length === 0) {
          this.$message.warning("请输入查询区间");
          return;
        }
        console.log(this.period)
        this.loading = true;
        let url = `/orders?page=${this.page}&pageSize=${this.pageSize}&startTime=${this.period[0].getTime()}&endTime=${this.period[1].getTime()}`;
        console.log(url)
        this.loadOrderList(url);
      },

      onPageChange() {

      },

      onPageSizeChange() {

      },

      deleteOrder() {

      },
    }
  }
</script>

<style scoped>

</style>