<template>
  <el-container>
    <el-aside>
      <el-steps :active="step" :space="200" finish-status="success" align-center direction="vertical">
        <el-step title="添加新产品" description="添加新产品的描述信息"></el-step>
        <el-step title="创建订单" description="创建新产品的订单"></el-step>
        <el-step title="完成" description="订单完成"></el-step>
      </el-steps>
    </el-aside>
    <el-main style="margin-top: 80px">
      <!--step 0-->
      <template v-if="step === 0">
        <el-form ref="addProductForm" :model="addProductForm" :rules="rules1" status-icon label-width="120px">
          <el-form-item label="产品名称" prop="name">
            <el-input v-model="addProductForm.name" prefix-icon="el-icon-user"></el-input>
          </el-form-item>
          <el-form-item label="供货商信息" prop="supplier">
            <el-input type="textarea" v-model="addProductForm.supplier" style="width: 400px" prefix-icon="el-icon-phone-outline"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="addProduct()" type="primary">添加产品</el-button>
            <el-button @click="resetAddProductForm()">重置表单</el-button>
          </el-form-item>
        </el-form>
      </template>
      <!--step 1 -->
      <template v-if="step === 1">
        <el-form ref="addOrderForm" :model="addOrderForm" :rules="rules2" status-icon label-width="120px">
          <el-form-item label="仓库名称" prop="wid">
            <el-select v-model="addOrderForm.wid" @change="addOrderForm.eid = ''" filterable>
              <el-option
                v-for="warehouse in warehouseList"
                :key="warehouse.id"
                :label="warehouse.name"
                :value="warehouse.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="产品名称" prop="pid">
            <el-input v-model="addProductForm.name" disabled></el-input>
          </el-form-item>
          <el-form-item label="操作员工" prop="eid">
            <template v-if="addOrderForm.wid !== ''">
              <el-select v-model="addOrderForm.eid" filterable>
                <el-option
                  v-for="operator in warehouseList.find(item => item.id === addOrderForm.wid).operators"
                  :key="operator.id"
                  :label="operator.name"
                  :value="operator.id">
                </el-option>
              </el-select>
            </template>
            <template v-else>
              <el-select v-model="addOrderForm.eid" disabled placeholder="请先选择仓库">
              </el-select>
            </template>
          </el-form-item>
          <el-form-item label="产品数量" prop="amount">
            <el-input-number v-model.number="addOrderForm.amount" :min="0" step-strictly></el-input-number>
          </el-form-item>
          <el-form-item>
            <el-button @click="addOrderNew()" type="primary">添加</el-button>
            <el-button @click="resetAddOrderForm()">重置</el-button>
          </el-form-item>
        </el-form>
      </template>
      <template v-if="step === 2">
        <el-button @click="step = 0" type="success" icon="el-icon-success" plain>订单创建成功，点击此处返回</el-button>
      </template>
    </el-main>

  </el-container>
</template>

<script>
  export default {
    name: "OrderAddNew",
    data() {
      const amountValidator = (rule, value, callback) => {
        if (value <= 0)
          callback(new Error('由于是新产品，订单数量必须为正数'));
        else
          callback();
      };
      return {
        step: 0,
        addProductForm: {
          name: "",
          supplier: ""
        },
        addOrderForm: {
          wid: "",
          pid: "",
          eid: "",
          amount: 0,
          status: "正常"
        },
        rules1: {
          name: [{required: true, message: "产品名称不能为空", trigger: "blur"}],
          supplier: [{required: true, message: "供货商不能为空", trigger: "blur"}],
        },
        rules2: {
          wid: [{required: true, message: "仓库名称不能为空", trigger: "blur"}],
          eid: [{required: true, message: "操作员工不能为空", trigger: "blur"}],
          amount: [{required: true, validator: amountValidator, trigger: 'change'}],
        },
        warehouseList: []
      }
    },

    methods: {
      loadWarehouseList(url) {
        let that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            console.log(res);
            that.warehouseList = res.data.warehouseList;
          } else {
            that.$message.error("仓库数据加载失败");
          }
        }).catch(res => {
          console.log(res);
          that.$message.error("服务器异常");
        });
      },

      loadProductId(url) {
        let that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            console.log(res);
            that.addOrderForm.pid = res.data;
          } else {
            that.$message.error("产品数据加载失败");
          }
        }).catch(res => {
          console.log(res);
          that.$message.error("服务器异常");
        });
      },

      addProduct() {
        this.$refs.addProductForm.validate(valid => {
          if (valid) {
            let that = this;
            this.$axios.post("/product/add", this.addProductForm).then(res => {
              if (res.status === 200) {
                if (res.data === 1) {
                  that.$message.success("新产品添加成功");
                  that.step++;
                  // 添加产品成功后查询该产品的id并传递到下一个表单
                  that.loadWarehouseList("/warehouses?pageSize=100");
                  that.loadProductId("/product/id?name=" + that.addProductForm.name);
                } else {
                  that.$message.warning("新产品添加成功");
                }
              } else if (res.status === 403) {
                that.$message.warning("权限不足，请联系管理员");
              }
            }).catch(err => {
              console.log(err);
              that.$message.error("服务器异常");
            });
          } else {
            return false;
          }
        });
      },

      addOrderNew() {
        this.$refs.addOrderForm.validate(valid => {
          if (valid) {
            var that = this;
            this.$axios.post("/order/add/new", this.addOrderForm).then(res => {
              if (res.status === 200) {
                if (res.data === 1) {
                  that.$message.success("订单添加成功");
                  that.resetAddProductForm();
                  that.resetAddOrderForm();
                  that.step++;
                } else {
                  that.$message.warning("订单添加失败");
                }
              } else if (res.status === 403) {
                that.$message.warning("权限不足，请联系管理员");
              }
            }).catch(err => {
              console.log(err);
              that.$message.error("服务器异常");
            });
          } else {
            return false;
          }
        });
      },

      resetAddProductForm() {
        this.addProductForm = {
          name: "",
          supplier: ""
        }
      },

      resetAddOrderForm() {
        this.addOrderForm = {
          pid: this.addOrderForm.pid,   // 从上一步传来的商品id不要变
          wid: '',
          eid: '',
          amount: 0,
          status: '正常',
        };
      }
    }
  };
</script>

<style>
</style>
