<script setup>
import { onMounted } from 'vue'
import { useRequestStore } from '../stores/requests'

const store = useRequestStore()

onMounted(() => {
  store.fetchRequests()
})
</script>

<template>
  <section>
    <h2>Dashboard</h2>

    <div class="grid">
      <div class="card">
        <h3>Total Applications</h3>
        <p>{{ store.requests.length }}</p>
      </div>

      <div class="card">
        <h3>Applied</h3>
        <p>{{ store.appliedCount }}</p>
      </div>

      <div class="card">
        <h3>Interviews</h3>
        <p>{{ store.interviewCount }}</p>
      </div>

      <div class="card">
        <h3>Offers</h3>
        <p>{{ store.offerCount }}</p>
      </div>

      <div class="card">
        <h3>High Priority</h3>
        <p>{{ store.highPriorityCount }}</p>
      </div>
    </div>
  </section>
</template>