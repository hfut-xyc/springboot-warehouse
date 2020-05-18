<template>
  <el-container>
    <el-header style="margin-top: 20px;">
      <el-row :gutter="12">
        <el-col :span="8">
          <el-input v-model="keyword" placeholder="通过用户名搜索" prefix-icon="el-icon-search"></el-input>
        </el-col>
        <el-col :span="3">
          <el-button @click="searchUser()" type="primary" icon="el-icon-search">查询</el-button>
        </el-col>
        <el-col :span="3">
          <el-button @click="isDialogVisible=true" type="success" icon="el-icon-plus" plain>添加新用户</el-button>
        </el-col>
      </el-row>
    </el-header>

    <el-table :data="userList" v-loading="loading" border stripe>
      <el-table-column prop="id" label="用户ID" sortable width="100"></el-table-column>
      <el-table-column prop="username" label="用户名" sortable width="150"></el-table-column>
      <el-table-column prop="phone" label="联系电话" width="120"></el-table-column>
      <el-table-column prop="registerTime" label="注册时间" sortable></el-table-column>
      <el-table-column prop="roles" label="角色权限">
        <template slot-scope="scope">
          <el-checkbox :checked="true" disabled>普通用户</el-checkbox>
          <el-checkbox v-model="isAdmin[scope.$index]" @change="changeRole(scope.row, scope.$index)">超级管理员</el-checkbox>
        </template>
      </el-table-column>
      <el-table-column prop="enabled" label="用户状态" width="150">
        <template slot-scope="scope">
          <el-switch
            @change="setUserEnabled(scope.row)"
            v-model="scope.row.enabled"
            active-text="启用"
            inactive-text="禁用">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="deleteUser(scope.row)" size="mini" icon="el-icon-delete" type="danger" plain>删除</el-button>
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

    <el-dialog title="添加新用户" :visible.sync="isDialogVisible">
      <el-form ref="addForm" :model="addForm" :rules="rules" status-icon label-width="120px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addForm.username" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addForm.password" show-password prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="addForm.confirmPassword" show-password prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="addForm.phone" prefix-icon="el-icon-phone"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addUser()" type="primary">添加</el-button>
        <el-button @click="isDialogVisible=false">取消</el-button>
      </div>
    </el-dialog>
  </el-container>
</template>

<script>
  export default {
    name: "User",
    data() {
      // 二次密码校验
      const validatorPassword = (rule, value, callback) => {
        if (value === '')
          callback(new Error('请再次输入密码'))
        else if (value !== this.addForm.password)
          callback(new Error('两次输入密码不一致'))
        else
          callback()
      };
      return {
        total: 0, // 查询到的用户总数
        page: 1, // 当前页码
        pageSize: 10, // 当前页面大小
        keyword: "", // 查询用户名的关键字
        userList: [], // 获得的查询结果
        loading: false, // 页面表格是否处于加载状态

        isDialogVisible: false,   // 添加用户的对话框是否可见
        addForm: {    // 添加用户表单
          username: "",
          password: "",
          confirmPassword: "",
          phone: ""
        },
        rules: {
          username: [{required: true, message: "用户名不能为空", trigger: "blur"}],
          password: [{required: true, message: "密码不能为空", trigger: "blur"}],
          confirmPassword: [{required: true, validator: validatorPassword, trigger: "blur"}],
          phone: [{required: true, message: "联系电话不能为空", trigger: "blur"}]
        }
      };
    },

    computed: {
      isAdmin: function () {
        var list = [];
        this.userList.forEach(user => {
          list.push(user.roles.length === 2);
        });
        return list;
      }
    },

    mounted: function () {
      this.loading = true;
      this.loadUserList("/users");
    },

    methods: {
      loadUserList(url) {
        var that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            console.log(res);
            that.userList = res.data.userList;
            that.total = res.data.total;
            that.loading = false;
            that.$message.success("数据加载成功");
          } else {
            that.loading = false;
            that.$message.error("数据加载失败");
          }
        })
          .catch(res => {
            that.loading = false;
            that.$message.error("服务器异常");
          });
      },

      searchUser() {
        if (this.keyword.trim() === "") {
          this.$message.warning("请输入关键字");
          return;
        }
        this.loading = true;
        var url = "/users?page=" + this.page + "&pageSize=" + this.pageSize + "&keyword=" + this.keyword.trim();
        this.loadUserList(url);
      },

      onPageChange(val) {
        this.page = val;
        this.loading = true;
        var url = "/users?page=" + this.page + "&pageSize=" + this.pageSize;
        if (this.keyword !== "") {
          url += "&keyword=" + this.keyword.trim();
        }
        this.loadUserList(url);
      },

      onPageSizeChange(val) {
        this.pageSize = val;
        if (this.pageSize * (this.page - 1) >= this.total) {
          this.page = 1;
          this.pageSize = 10;
        }
        this.loading = true;
        var url = "/users?page=" + this.page + "&pageSize=" + this.pageSize;
        if (this.keyword !== "") {
          url += "&keyword=" + this.keyword.trim();
        }
        this.loadUserList(url);
      },

      setUserEnabled(row) {
        this.$axios.post("/user/" + row.id + "/enabled" + "?enabled=" + row.enabled, {})
          .then(res => {
            console.log(res);
            this.$message.success("用户[" + row.username + "]状态已改变");
          })
          .catch(res => {
            console.log(res);
          });
      },

      addUser() {
        this.$refs["addForm"].validate(valid => {
          if (valid) {
            var that = this;
            this.$axios.post("/user/add", {
              username: this.addForm.username,
              password: this.addForm.password,
              phone: this.addForm.phone
            })
              .then(res => {
                if (res.status === 200) {
                  if (res.data === 1) {
                    that.$message.success("用户添加成功");
                    that.isDialogVisible = false;
                    that.addForm = {username: "", password: "", phone: ""};
                    that.loadUserList("/users");
                  } else {
                    that.$message.warning("用户添加失败, 用户名可能已存在");
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

      deleteUser(row) {
        this.$confirm("是否删除该用户?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          var that = this;
          this.$axios.delete("/user/" + row.id + "/delete")
            .then(res => {
              console.log(res);
              that.$message.success("用户[" + row.username + "]已删除");
              that.loadUserList("/users");
            })
            .catch(res => {
              console.log(res);
              that.$message.error("服务器异常");
            });
        });
      },

      changeRole(row, index) {
        console.log(this.isAdmin);
        var that = this;
        console.log(this.isAdmin[index]);
        this.$axios.post("/user/" + row.id + "/set-admin" + "?isAdmin=" + this.isAdmin[index], {})
          .then(res => {
            if (res.status === 200) {
              if (res.data === 1) {
                that.$message.success("用户[" + row.username + "]角色修改成功");
                that.loadUserList("/users");
              } else {
                that.$message.warning("修改用户角色失败");
              }
            }
          })
          .catch(err => {
            this.$message.error("服务器异常")
          });
      }
    }
  };
</script>

<style>
</style>
