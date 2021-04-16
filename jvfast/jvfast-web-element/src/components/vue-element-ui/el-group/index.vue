<template>
  <div
    v-if="display"
    :class="['avue-group',{'avue-group--card':card}]"
  >
    <el-row
      span="24"
      class="avue-group__item"
    >
      <div
        v-if="$slots.header&&header"
        class="avue-group__header"
      >
        <slot name="header" />
      </div>
      <div
        v-else-if="(label || icon)&&header"
        class="avue-group__header"
      >
        <i
          v-if="icon"
          :class="[icon,'avue-group__icon']"
        />
        <h1
          v-if="label"
          class="avue-group__title"
        >
          {{ label }}
        </h1>
      </div>

      <slot />
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'ElGroup',
  props: {
    header: {
      type: Boolean,
      default: true
    },
    icon: {
      type: String,
      default: ''
    },
    display: {
      type: Boolean,
      default: true
    },
    card: {
      type: Boolean,
      default: false
    },
    label: {
      type: String,
      default: ''
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
