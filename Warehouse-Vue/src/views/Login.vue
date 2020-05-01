<template>
  <div class="body">
    <el-form ref="loginForm" :model="loginForm" :rules="rules" class="login-box" status-icon>
      <h3 class="login-title">登录页面</h3>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" prefix-icon="el-icon-user" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          prefix-icon="el-icon-lock"
          placeholder="请输入密码"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="checked">记住用户</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button @click="login()" type="primary" icon="el-icon-s-promotion" class="btn">登录</el-button>
        <el-button @click="reset()" type="primary" plain icon="el-icon-refresh-right" class="btn">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { postRequest } from "../utils/api";

export default {
  name: "Login",
  data() {
    return {
      checked: false,
      loginForm: { username: "", password: "" },
      rules: {
        username: [
          { required: true, message: "账号不能为空", trigger: "blur" }
        ],
        password: [{ required: true, message: "密码不能为空", trigger: "blur" }]
      }
    };
  },

  methods: {
    login() {
      this.$refs["loginForm"].validate(valid => {
        if (valid) {
          var that = this;
          // axios所有的请求默认是json格式，登录必须封装formdata格式，因为spring security不接受json
          postRequest("/login", this.loginForm)
            .then(res => {
              console.log(res);
              if (res.data === "success") {
                // that.$store.commit("login", that.loginForm.username);
                that.$router.replace("/home");
                that.$message.success("登录成功^_^");
              } else {
                that.$message.warning("登录失败，用户名和密码不匹配T_T");
              }
            })
            .catch(error => {
              console.log(error);
              that.$message.error("服务器连接失败>_<");
            });
        } else {
          return false;
        }
      });
    },
    reset() {
      this.loginForm.username = "";
      this.loginForm.password = "";
    }
  }
};
</script>

<style>
.body {
  /* background-image: url("../assets/login.png"); */
  background-size: 100% 100%;
  background-repeat: no-repeat;
}

.login-box {
  border: 1px solid #dcdfe6;
  width: 300px;
  margin: 100px auto;
  padding: 35px 30px 15px 30px;
  border-radius: 10px;
  box-shadow: 0 0 25px #909399;
}

.login-title {
  text-align: center;
  font-size: 20px;
  margin: 0 auto 40px auto;
  color: #00aaff;
}

.btn {
  width: 45%;
  border-radius: 5px;
}
</style>