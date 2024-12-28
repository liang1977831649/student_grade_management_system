<script   setup>
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { searchServer, addServer, editServer, deleteServer } from "@/api/teacher.js"
import { ElMessageBox, ElMessage } from 'element-plus'

const tableData = ref([
  {
    no: '20160503',
    name: '李东旭',
    phone: '18933218989'
  },
  {
    no: '20160503',
    name: '李东旭',
    phone: '18933218989'
  },
  {
    no: '20160503',
    name: '李东旭',
    phone: '18933218989'
  },
])

// import type { ComponentSize } from 'element-plus'
// const size = ref<ComponentSize>('default')
const currentPage = ref(1)
const pageSize = ref(3)
const background = ref(false)
const disabled = ref(false)
const allNumber = ref(100)
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
  no: "",
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
  no: [
  {required: true, message: "编号不为空", trigger: 'blur'},
  {min:4,message:'编号最少4个数字',trigger:'blur'}
  ],
  name: [
    { required: true, message: '姓名不能为空', trigger: 'blur' },
    {min:2,message:'姓名最少4个字',trigger:'blur'}
  ],
  phone: [
    { required: true, message: '手机号不能为空', trigger: 'blur' },
    {min:11,max:11,message:'手机号码是11个数字',trigger:'blur'}
  ]
}

const searchButton = () => {
  searchBody.value.pageNum = 1;
  searchBody.value.pageSize = 5;
  search()
}

const reset = () => {
  searchBody.value.searchName = "";
  searchBody.value.no='';
}

const dialogVisible = ref(false)

const teacherModel = ref({
  id: "",
  no: "",
  name: "",
  phone: ""
})
const title = ref("添加")

const cleanModel = () => {
  teacherModel.value.id = '';
  teacherModel.value.no = '';
  teacherModel.value.name = '';
  teacherModel.value.phone = '';
}
const addButton = () => {
  title.value = '添加';
  dialogVisible.value = true;
}
const editButton = (row) => {
  title.value = '编辑';
  //赋值操作
  teacherModel.value.id = row.id;
  teacherModel.value.no = row.no;
  teacherModel.value.name = row.name;
  teacherModel.value.phone = row.phone;
  dialogVisible.value = true;
}
const deleteButton = async (row) => {
  ElMessageBox.confirm(
    '你确认要删除该教师信息吗?',
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
const AddAndEdit =  () => {
  form.value.validate((async valid => {
    if (valid) {
      if (title.value == '添加') {
        console.log(teacherModel.value);
        await addServer(teacherModel);
        ElMessage.success('添加成功')
      } else {
        console.log(teacherModel.value);
        await editServer(teacherModel);
        ElMessage.success('编辑成功')
      }
      await search();
      dialogVisible.value = false;
    }
  }))
}
</script>

<template>
  <div>
    <div>
      <el-input v-model="searchBody.no" style="width: 220px" placeholder="教师编号" :prefix-icon="Search" />
      <el-input v-model="searchBody.name" style="width: 220px;margin-left:10px" placeholder="教师姓名"
        :prefix-icon="Search" />
      <el-button type="primary" style="margin-left: 10px;" @click="searchButton">搜索</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div>
      <el-button type="primary" style="margin: 10px 0 10px 0;" @click="addButton()">新增</el-button>
    </div>
    <div>
      <el-table :data="tableData" stripe style="width: 100%">
        <el-table-column prop="no" label="教师编号" />
        <el-table-column prop="name" label="教师姓名" />
        <el-table-column prop="phone" label="教师电话" />
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
    <!-- 添加或者编辑弹窗 -->
    <div>
      <el-dialog v-model="dialogVisible" :title="title" width="350px">
        <el-form :model="teacherModel" :rules="rules" ref="form">
          <el-form-item label="教师编号" prop="no">
            <el-input v-model="teacherModel.no" />
          </el-form-item>
          <el-form-item label="教师姓名" prop="name">
            <el-input v-model="teacherModel.name" />
          </el-form-item>
          <el-form-item label="手机号码" prop="phone">
            <el-input v-model="teacherModel.phone" />
          </el-form-item>
        </el-form>

        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialogVisible = false; cleanModel()">取消</el-button>
            <el-button type="primary" @click="AddAndEdit()">
              确定
            </el-button>
          </div>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<style scoped></style>

