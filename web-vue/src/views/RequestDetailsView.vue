<script setup>
import { computed, onMounted } from 'vue'
import { useRequestStore } from '../stores/requests'

const props = defineProps({
  id: String
})

const store = useRequestStore()

onMounted(() => {
  if (!store.requests.length) {
    store.fetchRequests()
  }
})

const application = computed(() =>
  store.requests.find(
    item => String(item.id) === props.id
  )
)
</script>

<template>
  <div
    class="card"
    v-if="application"
  >

    <h2>
      {{ application.companyName }}
    </h2>

    <p>
      <strong>Position:</strong>
      {{ application.positionTitle }}
    </p>

    <p>
      <strong>Category:</strong>
      {{ application.category }}
    </p>

    <p>
      <strong>Application Date:</strong>
      {{ application.applicationDate }}
    </p>

    <p>
      <strong>Closing Date:</strong>
      {{ application.closingDate }}
    </p>

    <p>
      <strong>Status:</strong>
      {{ application.status }}
    </p>

    <p>
      <strong>Priority:</strong>
      {{ application.priority }}
    </p>

    <p>
      <strong>Contact Person:</strong>
      {{ application.contactPerson || 'Not provided' }}
    </p>

    <p>
      <strong>Contact Email:</strong>
      {{ application.contactEmail || 'Not provided' }}
    </p>

    <p>
      <strong>Interview Date:</strong>
      {{ application.interviewDate || 'Not scheduled' }}
    </p>

    <p>
      <strong>Notes:</strong>
      {{ application.notes || 'No notes added' }}
    </p>

    <RouterLink to="/applications">
      Back to Applications
    </RouterLink>

  </div>

  <p v-else>
    Application not found or still loading.
  </p>
</template>