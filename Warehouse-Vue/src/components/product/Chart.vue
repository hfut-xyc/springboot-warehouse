<template>
  <div>
    <ve-line :data="orderChart" :settings="settings"></ve-line>
    <ve-pie :data="productChart"></ve-pie>
  </div>
</template>

<script>
  export default {
    name: "Chart",
    data: function () {
      return {
        settings: {
          labelMap: {
            count: '当日订单总数'
          }
        },
        orderChart: {
          columns: ['date', 'count'],
          rows: []
        },
        productChart: {
          columns: ['name', 'total'],
          rows: []
        }
      }
    },
    mounted() {
      this.loadOrderChart("/order/chart")
      this.loadProductChart("/products")
    },
    methods: {
      loadOrderChart(url) {
        let that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            console.log(res.data);
            that.orderChart.rows = res.data;
            that.$message.success("订单数据加载成功");
          } else {
            that.$message.error("订单数据加载失败");
          }
        }).catch(res => {
          that.$message.error("服务器异常");
        });
      },

      loadProductChart(url) {
        let that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            res.data.productList.forEach(item => {
              that.productChart.rows.push({'name': item.name, 'total': item.total});
            });
            that.$message.success("产品数据加载成功");
          } else {
            that.$message.error("产品数据加载失败");
          }
        }).catch(res => {
          that.$message.error("服务器异常");
        });
      }
    }
  };
</script>

<style>
</style>
