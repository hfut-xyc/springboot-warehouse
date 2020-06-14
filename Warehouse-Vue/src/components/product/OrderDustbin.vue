<template>
  <el-container>
    <el-header style="margin-top: 10px">
      <el-date-picker v-model="period" :editable="false"
                      type="datetimerange"
                      range-separator="至"
                      start-placeholder="开始时间"
                      end-placeholder="结束时间">
      </el-date-picker>
      <el-button @click="searchOrder()" type="primary" style="margin-left: 20px" icon="el-icon-search">按创建时间查询</el-button>
    </el-header>

    <el-table :data="orderList" v-loading="loading" border stripe>
      <el-table-column prop="id" label="订单编号" sortable fixed></el-table-column>
      <el-table-column prop="eid" label="负责人编号" sortable width="120"></el-table-column>
      <el-table-column prop="wid" label="仓库编号" sortable></el-table-column>
      <el-table-column prop="pid" label="产品编号" sortable></el-table-column>
      <el-table-column prop="amount" label="订单数量"
                       :filters="[{ text: '入库订单', value: 'input' }, { text: '出库订单', value: 'output' }]"
                       :filter-method="orderFilter">
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" sortable width="180"></el-table-column>
      <el-table-column label="操作" width="180" fixed="right">
        <template slot-scope="scope">
          <el-button @click="recoverOrder(scope.row)" size="mini" icon="el-icon-refresh" type="success" plain>恢复
          </el-button>
          <el-button @click="deleteOrder(scope.row)" size="mini" icon="el-icon-delete" type="danger" plain>删除
          </el-button>
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
    name: "OrderDustbin",
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
      this.loadOrderList("/orders?status=报废")
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
            that.$message.success("订单数据加载成功");
          } else {
            that.loading = false;
            that.$message.error("订单数据加载失败");
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
        let url = `/orders?page=${this.page}&pageSize=${this.pageSize}&status=报废&startTime=${this.period[0].getTime()}&endTime=${this.period[1].getTime()}`;
        console.log(url)
        this.loadOrderList(url);
      },

      onPageChange(val) {
        this.page = val;
        this.loading = true;
        let url = "/orders?page=" + this.page + "&pageSize=" + this.pageSize + "&status=报废";
        if (this.period.length !== 0) {
          url += "&startTime=" + this.period[0].getTime() + "&endTime=" + this.period[1].getTime();
        }
        this.loadOrderList(url);
      },

      onPageSizeChange(val) {
        this.pageSize = val;
        if (this.pageSize * (this.page - 1) >= this.total) {
          this.page = 1;
          this.pageSize = 10;
        }
        this.loading = true;
        let url = "/orders?page=" + this.page + "&pageSize=" + this.pageSize + "&status=报废";
        if (this.period.length !== 0) {
          url += "&startTime=" + this.period[0].getTime() + "&endTime=" + this.period[1].getTime();
        }
        this.loadOrderList(url);
      },

      orderFilter(value, row) {
        return value === "input" ? row.amount > 0 : row.amount < 0;
      },

      recoverOrder(row) {
        this.$confirm("是否要恢复该订单?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "info"
        }).then(() => {
          let that = this;
          let url = "/order/" + row.id +"/update/status?status=正常";
          this.$axios.post(url, {}).then(res => {
            if (res.status === 200) {
              if (res.data === 1) {
                this.$message.success("订单恢复成功");
                that.loadOrderList("/orders?page=" + this.page + "&pageSize=" + this.pageSize + "&status=报废");
              } else {
                this.$message.warning("订单恢复失败");
              }
            }
          }).catch(err => {
            console.log(err);
            that.$message.error("服务器异常");
          });
        });
      },

      deleteOrder(row) {
        this.$confirm("是否要彻底删除该订单?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let that = this;
          this.$axios.delete("/order/" + row.id + "/delete").then(res => {
            if (res.status === 200) {
              if (res.data === 1) {
                that.$message.success("订单删除成功");
                that.loadOrderList("/orders?page=" + this.page + "&pageSize=" + this.pageSize + "&status=报废");
              } else {
                that.$message.warning("订单删除失败");
              }
            }
          }).catch(err => {
            console.log(err);
            that.$message.error("服务器异常");
          });
        });
      }
    }
  }
</script>

<style scoped>

</style>