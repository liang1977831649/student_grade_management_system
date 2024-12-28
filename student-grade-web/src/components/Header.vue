<script setup>
import { ref } from "vue"
import { getUserInfoServer } from "@/api/userInfo.js"
import userInfoServer from "@/store/useInfo.js"
import { useToken } from "@/store/token.js"
import { useRouter } from "vue-router"
import { ElMessage, ElMessageBox } from "element-plus"
import dynamicRouter from "@/store/routerStore"
import axios from "axios";
import { defineExpose } from 'vue'
import {
  SwitchButton,
  EditPen,
  User}  from '@element-plus/icons-vue'

const defaultAvatar = ref("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png")

//获取个人资料
const user = userInfoServer();
const getUserInfo = async () => {
    const result = await getUserInfoServer();
    console.log("result.data=", result.data);
    user.setUser(result.data)
    //加载用户头像
    await loadTemUrl(user.userInfo.avatar);
}
getUserInfo();

const tempUrl = ref(null);
// 加载temUrl的方法，当要加载头像时，都会重新更新temUrl
const loadTemUrl = async (avatar) => {
    console.log("avatar===", avatar);
    const response = await axios.get(avatar, {
        headers: {
            Authorization: token.token,
        },
        responseType: "blob" //将响应直接存储为 Blob 对象
    });
    // 通过 axios 请求获取到的图片数据的 Blob 转换为一个临时的 URL
    tempUrl.value = URL.createObjectURL(response.data);
}

//获取user
const token = useToken();

//获取路由对象
const router = useRouter();

//获取路由表
const useDynamicRouter = dynamicRouter();

//头像的下拉菜单
const handleCommand = (command) => {
    console.log("command=", command);
    if (command === 'logout') {
        ElMessageBox.confirm(
            '你确认要退出登录吗?',
            '温馨提示',
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: "warning",
            })
            .then(async () => {
                token.removeToken();//清除token
                user.removeUser();//清除user
                useDynamicRouter.removeRouter();//清楚路由信息
                router.push('/')
            })
            .catch(() => {
                ElMessage.info('取消了退出')
            })
    }
    else {
        router.push('/' + command);
    }
}
//定义方法
const testLoad = (avatar) => {
    console.log("avatar@@@=", avatar);
    //换头像
    loadTemUrl(avatar)
}
//暴露方法出来
defineExpose({
    testLoad
})

</script>
<template>
    <div>
        <span>学生成绩管理系统</span>
    </div>
    <div>
        <div style="float: left; margin-right: 20px;margin-top: 5px;"><strong>{{ user.userInfo.name }}</strong></div>
        <el-dropdown style="float: right;" placement="bottom-end" @command="handleCommand">
            <el-avatar :src="tempUrl ? tempUrl : defaultAvatar" />
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item v-if="user.userInfo.role==1" command="userInfo" :icon="User">基本资料</el-dropdown-item>
                    <el-dropdown-item command="password" :icon="EditPen">重置密码</el-dropdown-item>
                    <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
    </div>
</template>
<style lang="less" scoped>
.el-dropdown__box {
    display: flex;
    align-items: center;

    .el-icon {
        color: #999;
        margin-left: 10px;
    }

    &:active,
    &:focus {
        outline: none;
    }
}
</style>