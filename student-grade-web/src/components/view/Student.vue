<script   setup>
import { ref } from 'vue'
import { Search, Lock, CircleCloseFilled } from '@element-plus/icons-vue'
import { searchServer, addServer, editServer, deleteServer } from "@/api/student.js"
import { ElMessageBox, ElMessage, ElButton, ElDialog } from 'element-plus'
import { useToken } from "@/store/token.js"
import axios from "axios";
const tableData = ref([
  {
    id: '20160503',
    name: '李东旭',
    sex: '0',
    phone: '18933218989',
    birthday: '2001-11-12'
  },
  {
    id: '20160503',
    name: '李东旭',
    sex: '0',
    phone: '18933218989',
    birthday: '2001-11-12'
  },
  {
    id: '20160503',
    name: '李东旭',
    sex: '0',
    phone: '18933218989',
    birthday: '2001-11-12'
  },
])


const currentPage = ref(1)
const pageSize = ref(3)
const background = ref(false)
const disabled = ref(false)
const allNumber = ref(100)

const token = useToken();

const handleSizeChange = async (number) => {
  console.log(" items per page", number)
  searchBody.value.pageSize = number;
  await search()
}

const handleCurrentChange = async (number) => {
  console.log("current page: ", number)
  searchBody.value.pageNum = number
  await search()
}
// 搜索体
const searchBody = ref({
  name: "",
  id: "",
  pageNum: 1,
  pageSize: 3
})

//-----定义函数-----
const search = async () => {
  const result = await searchServer(searchBody);
  tableData.value = result.data.items;
  allNumber.value = result.data.total;
}

search();

const rules = {
  id: [
    { required: true, message: "学号不为空", trigger: 'blur' },
    { min: 6, message: '学号最少6个数字', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '姓名不能为空', trigger: 'blur' },
    { min: 2, message: '姓名最少4个字', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

const searchButton = () => {
  searchBody.value.pageNum = 1;
  searchBody.value.pageSize = 3;
  search()
}

const reset = () => {
  searchBody.value.name = "";
  searchBody.value.id = '';
}

const dialogVisible = ref(false)

const studentModel = ref({
  id: "",
  name: "",
  password: "",
  sex: "",
  phone: "",
  birthday: "",
  role: "",
  avatar: "",
})
const title = ref("添加")

const cleanModel = () => {
  studentModel.value.id = '';
  studentModel.value.sex = '';
  studentModel.value.name = '';
  studentModel.value.phone = '';
  studentModel.value.password = '';
  studentModel.value.birthday = '';
  studentModel.value.role = '';
  studentModel.value.avatar = '';
}

const addButton = () => {
  cleanModel();
  title.value = '添加';
  dialogVisible.value = true;
}

const editButton = (row) => {
  title.value = '编辑';
  //赋值操作
  console.log("row=", row);
  studentModel.value.id = row.id + "";
  studentModel.value.name = row.name;
  studentModel.value.sex = row.sex + "";
  studentModel.value.birthday = row.birthday;
  studentModel.value.phone = row.phone;
  studentModel.value.role = row.role;
  studentModel.value.avatar = row.avatar;
  // 改变temUrl的内容，称为最新的
  loadTemUrl(row.avatar)
  dialogVisible.value = true;
}

const deleteButton = async (row) => {
  ElMessageBox.confirm(
    '你确认要删除该学生信息吗?',
    '温馨提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: "warning",
    })
    .then(async () => {
      await deleteServer(row.id);
      await search()
      ElMessage.success("删除成功")
    })
    .catch(() => {
      ElMessage.info('用户取消了删除')
    })
}

const form = ref(null);
const AddAndEdit = () => {
  form.value.validate((async valid => {
    if (valid) {
      if (title.value == '添加') {
        console.log(studentModel.value);
        await addServer(studentModel);
        ElMessage.success('添加成功')
      } else {
        console.log(studentModel.value);
        await editServer(studentModel);
        ElMessage.success('编辑成功')
      }
      cleanModel();
      await search();
      dialogVisible.value = false;
    }
  }))
}

const addOrEditCancel = () => {
  cleanModel();
  dialogVisible.value = false;
  form.value.resetFields();
}

//上传头像
const loadAvatar = async(res) => {
  const path = res.data;
  console.log("上传头像成功：", path);
  studentModel.value.avatar = path;
  console.log("student=", studentModel.value);
  //重新更新temUrl
  await loadTemUrl(path)
}

const imgVisible = ref(false);
const tempUrl = ref(null);


const showAvatar = async (avatar) => {
  //重新更新temUrl
  loadTemUrl(avatar)
  imgVisible.value = true;
}

// 加载temUrl的方法，当要加载头像时，都会重新更新temUrl
const loadTemUrl=async(avatar)=>{
  const response = await axios.get(avatar, {
    headers: {
      Authorization: token.token,
    },
    responseType: "blob" //将响应直接存储为 Blob 对象
  });
  // 通过 axios 请求获取到的图片数据的 Blob 转换为一个临时的 URL
  tempUrl.value = URL.createObjectURL(response.data);
}
</script>

<template>
  <div>
    <div>
      <el-input v-model="searchBody.id" style="width: 220px" placeholder="学生学号" :prefix-icon="Search" />
      <el-input v-model="searchBody.name" style="width: 220px;margin-left:10px" placeholder="学生姓名"
        :prefix-icon="Search" />
      <el-button type="primary" style="margin-left: 10px;" @click="searchButton">搜索</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div>
      <el-button type="primary" style="margin: 10px 0 10px 0;" @click="addButton()">新增</el-button>
    </div>
    <div>
      <el-table :data="tableData" stripe style="width: 100%">
        <el-table-column prop="id" label="学号" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="phone" label="电话" />
        <el-table-column prop="sex" label="性别">
          <template #default="scope">
            <el-tag :type="scope.row.sex == 1 ? 'success' : 'primary'" disable-transitions>
              {{ scope.row.sex == 1 ? '男' : '女' }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="birthday" label="生日" />

        <!-- <el-table-column  label="头像"> -->
        <!-- <template #default="scope"> -->
        <!-- <TokenImg :src="scope.row.avatar" /> -->
        <!-- {{ scope.row.avatar }}   -->
        <!-- <AuthImag :imgUrl="scope.row.avatar" /> -->
        <!-- <AuthImg :imgUrl="scope.row.avatar" style="width: 100px;"></AuthImg> -->
        <!-- </template>
        </el-table-column> -->

        <el-table-column label="头像展示">
          <template #default="scope">
            <el-button type="info" plain @click="showAvatar(scope.row.avatar)">
              预览
            </el-button>
          </template>
        </el-table-column>

        <el-table-column fixed="right" label="操作" min-width="120">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="editButton(row)">
              编辑
            </el-button>
            <el-button type="danger" size="small" @click="deleteButton(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>



    <div>
      <!--分页插件-->
      <el-pagination style="float: right;
      margin-top: 20px" v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[3, 5, 10]"
        :disabled="disabled" :background="background" layout="prev, pager, next, jumper,total, sizes" :total="allNumber"
        @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>

    <!-- 窗口弹窗 -->
    <el-dialog v-model="imgVisible" :show-close="false" width="300">
      <template #header="{ close, titleId, titleClass }">
        <div class="my-header">
          <h4 :id="titleId" :class="titleClass">图片预览</h4>
          <el-button type="danger" @click="close">
            <el-icon class="el-icon--left">
              <CircleCloseFilled />
            </el-icon>
            关闭
          </el-button>
        </div>
      </template>
      <img :src="tempUrl" width="200px">
    </el-dialog>

    <!-- 添加或者编辑弹窗 -->
    <div>
      <el-dialog v-model="dialogVisible" :title="title" width="350px">
        <el-form :model="studentModel" :rules="rules" ref="form">

          <!-- 添加用户时，可以对学号修改，但是更新用户时，不能对学号这一栏修改 -->
          <el-form-item v-if="title == '添加'" label="学号" prop="id">
            <el-input v-model="studentModel.id" />
          </el-form-item>

          <el-form-item v-else-if="title == '编辑'" label="学号" prop="id">
            <el-input v-model="studentModel.id" disabled="true" />
          </el-form-item>

          <el-form-item label="姓名" prop="name">
            <el-input v-model="studentModel.name" />
          </el-form-item>

          <el-form-item label="密码" v-if="title == '添加'" prop="password">
            <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="studentModel.password"></el-input>
          </el-form-item>

          <!-- 在该系统中，如果是添加，那密码必须填写，如果是修改，那么密码可以填写，也可以不填写 -->
          <el-form-item label="密码" v-else>
            <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="studentModel.password"></el-input>
          </el-form-item>

          <el-form-item label="手机号">
            <el-input v-model="studentModel.phone" />
          </el-form-item>

          <el-form-item label="性别">
            <el-radio-group v-model="studentModel.sex">
              <el-radio value=0>女</el-radio>
              <el-radio value=1>男</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="出生日期">
            <el-date-picker v-model="studentModel.birthday" type="date" value-format="YYYY-MM-DD HH:mm:ss"
              format="YYYY-MM-DD" placeholder="选择日期" :size="small" />
          </el-form-item>
        </el-form>

        <el-form-item label="学生头像">
          <el-upload action="http://localhost:8080/file/upload" :show-file-list="false" :on-success="loadAvatar"
            :headers="{ 'Authorization': token.token }" name="multipartFile" :auto-upload="true">
            <el-button type="primary">点击上传</el-button>
            <!-- 上传头像 -->
            <!-- <TokenImg v-if="studentModel.avatar" :src="studentModel.avatar" style="width: 100px;" /> -->
             <img :src="tempUrl" width="100px"/>
          </el-upload>
        </el-form-item>

        <template #footer>
          <div class="dialog-footer">
            <el-button @click="addOrEditCancel()">取消</el-button>
            <el-button type="primary" @click="AddAndEdit()">
              确定
            </el-button>
          </div>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<style scoped>
.my-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  gap: 16px;
}
</style>

