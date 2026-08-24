<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <h1>UG Internship Application Tracker</h1>

    <nav>
      <RouterLink to="/dashboard">Dashboard</RouterLink>
      <RouterLink to="/applications">Applications</RouterLink>
      <RouterLink to="/about">About</RouterLink>
    </nav>
  </header>

  <main>
    <RouterView />
  </main>
</template>