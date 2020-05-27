<template>
  <el-container style="text-align: center; width: 400px; margin-left: auto; margin-right: auto;">
    <el-main>
    <el-form ref="addOldForm" :model="addOldForm" :rules="rules" label-width="100px">
      <el-form-item label="仓库名称" prop="wid">
        <el-select v-model="addOldForm.wid" value-key="id" :loading="warehouseLoading">
          <el-option
            v-for="warehouse in warehouseList"
            :key="warehouse.id"
            :label="warehouse.name"
            :value="warehouse.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商品名称" prop="pid">
        <el-select v-model="addOldForm.pid" value-key="id" :loading="productLoading">
          <el-option
            v-for="product in productList"
            :key="product.id"
            :label="product.name"
            :value="product.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="操作员工" prop="eid">
        <template v-if="addOldForm.wid !== ''">
          <el-select v-model="addOldForm.eid" value-key="id" :loading="warehouseLoading">
            <el-option
              v-for="operator in warehouseList[addOldForm.wid].operators"
              :key="operator.id"
              :label="operator.name"
              :value="operator.id">
            </el-option>
          </el-select>
        </template>
        <template v-else>
          <el-select v-model="addOldForm.eid" disabled placeholder="请先选择仓库">
          </el-select>
        </template>
      </el-form-item>
      <el-form-item label="商品数量" prop="amount">
        <el-input-number v-model.number="addOldForm.amount" width="200px">
        </el-input-number>
      </el-form-item>
    </el-form>
    </el-main>
    <el-footer>
      <el-button @click="addOrderOld()" type="primary">添加</el-button>
      <el-button @click="resetAddOldForm()">重置</el-button>
    </el-footer>
  </el-container>
</template>

<script>
  export default {
    name: "OrderAddOld",
    data() {
      const amountValidator = (rule, value, callback) => {
        if (value == 0)
          callback(new Error('商品数量不能为0'))
        else
          callback()
      };
      return {
        addOldForm: {
          wid: '',
          eid: '',
          pid: '',
          amount: 0,
          status: '正常',
        },
        rules: {
          wid: [{required: true, message: "仓库名称不能为空", trigger: "blur"}],
          eid: [{required: true, message: "操作员工不能为空", trigger: "blur"}],
          pid: [{required: true, message: "商品名称不能为空", trigger: "blur"}],
          amount: [{type: "number", message: "商品数量必须为数字"}],
        },

        // 产品列表，此处包含了目前已经有的产品及数量
        productList: [],
        // 员工列表，这里包括了仓库的员工。
        warehouseList: [],
        productLoading: false,
        warehouseLoading: false,
      }
    },

    mounted() {
      this.loadProductList('/products?pageSize=100');
      this.loadWarehouseList('/warehouses?pageSize=100');
    },

    methods: {
      loadProductList(url) {
        this.productLoading = true;
        var that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            console.log(res);
            that.productList = res.data.productList;
            that.productLoading = false;
          } else {
            that.productLoading = false;
            that.$message.error("数据加载失败");
          }
        }).catch(res => {
          console.log(res);
          that.productLoading = false;
          that.$message.error("服务器异常");
        });
      },


      loadWarehouseList(url) {
        this.warehouseLoading = true;
        var that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            console.log(res);
            that.warehouseList = res.data.warehouseList;
            that.warehouseLoading = false;
          } else {
            that.warehouseLoading = false;
            that.$message.error("数据加载失败");
          }
        })
        .catch(res => {
          console.log(res);
          that.warehouseLoading = false;
          that.$message.error("服务器异常");
        });
      },

      addOrderOld() {
        // let date = new Date()
        // this.addOldForm.createTime = date.toISOString().replace('T', ' ').split('.')[0];
        // this.addOldForm.updateTime = date.toISOString().replace('T', ' ').split('.')[0];
        this.$refs.addOldForm.validate(valid => {
          if (valid) {
            var that = this;
            this.$axios.post("/order/add/old", this.addOldForm)
            .then(res => {
              if (res.status === 200) {
                if (res.data === 1) {
                  that.$message.success("订单添加成功");
                  that.resetAddOldForm();
                } else {
                  that.$message.warning("订单添加失败");
                }
              } else if (res.status === 403) {
                that.$message.warning("权限不足，请联系管理员");
              }
            })
            .catch(err => {
              console.log(err);
              that.$message.error("服务器异常");
            });
          } else {
            return false;
          }
        });
      },

      resetAddOldForm() {
        this.addOldForm = {
          wid: '',
          eid: '',
          pid: '',
          amount: 0,
          status: '正常',
        };
      }
    }
  }
</script>

<style scoped>

</style>