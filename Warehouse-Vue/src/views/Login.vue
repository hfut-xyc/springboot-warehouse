<template>
  <div class="body">
    <el-form ref="loginForm" :model="loginForm" :rules="rules" class="login-box" status-icon>
      <h3 class="login-title">登录页面</h3>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" prefix-icon="el-icon-user" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" show-password prefix-icon="el-icon-lock" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item prop="verifyCode">
        <el-input v-model="loginForm.verifyCode" style="width: 150px; vertical-align: middle;" prefix-icon="el-icon-picture-outline-round" placeholder="请输入验证码"></el-input>
        <img id="captcha" @click="" src="http://localhost:8081/captcha"  alt="" title="看不清楚？换一张" style="cursor: pointer"/>
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
  import {postRequest} from "../utils/api";

  export default {
    name: "Login",
    data() {
      return {
        checked: false,
        loginForm: {username: this.$store.state.username, password: "", verifyCode: ""},
        rules: {
          username: [{required: true, message: "账号不能为空", trigger: "blur"}],
          password: [{required: true, message: "密码不能为空", trigger: "blur"}],
          verifyCode: [{required: true, message: "验证码不能为空", trigger: "blur"}],
        }
      };
    },

    methods: {
      login() {
        this.$refs["loginForm"].validate(valid => {
          if (valid) {
            var that = this;
            // axios所有的请求默认是json格式，登录必须封装formdata格式，因为spring security不接受json
            postRequest("/login", this.loginForm).then(res => {
                console.log(res);
                if (res.data === "success") {
                  that.$store.commit("login", {'username': that.loginForm.username, 'checked': that.checked});
                  that.$router.replace("/home");
                  that.$message.success("登录成功^_^");
                } else if (res.data === "fail") {
                  that.$message.warning("登录失败，用户名和密码不匹配T_T");
                }
              })
              .catch(error => {
                console.log(error);
                that.$message.error("服务器异常");
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

  #captcha {
    display: inline-block;
    vertical-align: middle;
    -webkit-appearance: none;
    background-image: none;
    border-radius: 4px;
    border: 1px solid;
    border-color: #dcdfe6;
    box-sizing: border-box;
    display: inline-block;
    font-size: inherit;
    height: 40px;
    outline: none;
    
    margin-left: 19px;
    text-align: right;
    transition: border-color .2s cubic-bezier(.645,.045,.355,1)
  }

  #captcha:hover {
    border-color: #C0C4CC;
  }

  #captcha:focus {
    border-color: #bad5f3;
  }
</style>