<script setup>
import userInfoServer from "@/store/useInfo.js"
import { ref } from "vue"
import { useToken } from "@/store/token"
import axios from "axios";
import { editServer } from "@/api/student.js"
import { ElMessage } from "element-plus";


const useUserInfo = userInfoServer();
const userInfo = ref({
    id: '',
    name: '',
    birthday: '',
    phone: '',
    role: '',
    sex: '',
    avatar: ''
})
const token = useToken();
const tempUrl = ref(null);

// 加载temUrl的方法，当要加载头像时，都会重新更新temUrl
const loadTemUrl = async (avatar) => {
    const response = await axios.get(avatar, {
        headers: {
            Authorization: token.token,
        },
        responseType: "blob" //将响应直接存储为 Blob 对象
    });
    // 通过 axios 请求获取到的图片数据的 Blob 转换为一个临时的 URL
    tempUrl.value = URL.createObjectURL(response.data);
}

const loadUserInfo = () => {
    //深拷贝，不能直接将store的信息复制到对象中（浅拷贝）
    userInfo.value.id = useUserInfo.userInfo.id;
    userInfo.value.name = useUserInfo.userInfo.name;
    userInfo.value.birthday = useUserInfo.userInfo.birthday;
    userInfo.value.phone = useUserInfo.userInfo.phone;
    userInfo.value.role = useUserInfo.userInfo.role;
    userInfo.value.sex = useUserInfo.userInfo.sex + '';
    userInfo.value.avatar = useUserInfo.userInfo.avatar;

    // userInfo.value=useUserInfo.userInfo;
    //改一下性别成为字符串
    userInfo.value.sex = userInfo.value.sex + '';
    //加载头像
    loadTemUrl(userInfo.value.avatar)
}
loadUserInfo();

// 加载用户头像,也是修改用户头像的函数
const loadAvatar = async (res) => {
    userInfo.value.avatar = res.data
    console.log("userInfo=", userInfo.value);
    await loadTemUrl(res.data)
    
}

const updateUserInfo = async () => {
    // console.log("userInfo=",userInfo.value);
    await editServer(userInfo);
    //将新的信息更新至useUserInfo
    useUserInfo.setUser(userInfo.value);
    callFather();
    ElMessage.success("修改信息成功")
}



const emit = defineEmits()
const callFather = () => {
    //fatherMethod 就是调用<router-view @father="method">用户，第二个就是传递参数。
    emit('fatherMethod', userInfo.value.avatar)  // 向父组件发送事件
}

</script>

<template>
    <div>
        <el-form :model="userInfo" ref="form">
            <!-- 添加用户时，可以对学号修改，但是更新用户时，不能对学号这一栏修改 -->
            <el-form-item label="学号">
                <el-input v-model="userInfo.id" disabled="true" />
            </el-form-item>

            <el-form-item label="姓名">
                <el-input v-model="userInfo.name" />
            </el-form-item>

            <el-form-item label="手机号">
                <el-input v-model="userInfo.phone" />
            </el-form-item>

            <el-form-item label="性别">
                <el-radio-group v-model="userInfo.sex">
                    <el-radio value=0>女</el-radio>
                    <el-radio value=1>男</el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item label="出生日期">
                <el-date-picker v-model="userInfo.birthday" type="date" value-format="YYYY-MM-DD HH:mm:ss"
                    format="YYYY-MM-DD" placeholder="选择日期" :size="small" />
            </el-form-item>
        </el-form>

        <el-form-item label="学生头像">
            <el-upload action="http://localhost:8080/file/upload" :show-file-list="false" :on-success="loadAvatar"
                :headers="{ 'Authorization': token.token }" name="multipartFile" :auto-upload="true">
                <el-button type="primary">点击上传</el-button>
                <!-- 上传头像 -->
                <img :src="tempUrl" width="100px" />
            </el-upload>
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="updateUserInfo()">确认修改</el-button>
        </el-form-item>
    </div>
</template>
<style scoped></style>
