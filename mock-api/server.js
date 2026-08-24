const http = require('http')

const PORT = 3000

function dateFromNow(days) {
  const date = new Date()
  date.setDate(date.getDate() + days)
  return date.toISOString().split('T')[0]
}

function calculatePriority(application) {

  if (
    application.status === 'ACCEPTED' ||
    application.status === 'REJECTED' ||
    application.status === 'WITHDRAWN'
  ) {
    return 'LOW'
  }

  if (!application.closingDate) {
    return 'LOW'
  }

  const today = new Date()
  const closingDate = new Date(application.closingDate)

  const difference =
    closingDate.getTime() - today.getTime()

  const daysRemaining =
    Math.ceil(difference / (1000 * 60 * 60 * 24))

  if (daysRemaining <= 7) {
    return 'HIGH'
  }

  if (daysRemaining <= 14) {
    return 'MEDIUM'
  }

  return 'LOW'
}

let requests = [
  {
    id: 1,
    companyName: 'GTT',
    positionTitle: 'Software Development Intern',
    category: 'INFORMATION_TECHNOLOGY',
    applicationDate: dateFromNow(-7),
    closingDate: dateFromNow(14),
    status: 'APPLIED',
    priority: 'MEDIUM',
    contactPerson: 'HR Department',
    contactEmail: 'careers@gtt.co.gy',
    interviewDate: '',
    notes: 'Application submitted online.'
  },
  {
    id: 2,
    companyName: 'Demerara Bank',
    positionTitle: 'Information Systems Intern',
    category: 'INFORMATION_TECHNOLOGY',
    applicationDate: dateFromNow(-3),
    closingDate: dateFromNow(5),
    status: 'INTERESTED',
    priority: 'HIGH',
    contactPerson: 'Human Resources',
    contactEmail: 'hr@example.com',
    interviewDate: '',
    notes: 'Prepare CV and application letter.'
  }
]

let nextId = 3

function send(res, status, data) {

  res.writeHead(status, {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Headers': 'Content-Type',
    'Access-Control-Allow-Methods':
      'GET,POST,PATCH,DELETE,OPTIONS'
  })

  res.end(
    data === undefined
      ? ''
      : JSON.stringify(data)
  )
}

function body(req) {

  return new Promise((resolve, reject) => {

    let content = ''

    req.on('data', chunk => {
      content += chunk
    })

    req.on('end', () => {

      try {
        resolve(
          content
            ? JSON.parse(content)
            : {}
        )
      } catch (error) {
        reject(error)
      }
    })
  })
}

http.createServer(async (req, res) => {

  if (req.method === 'OPTIONS') {
    return send(res, 204)
  }

  const url =
    new URL(
      req.url,
      `http://${req.headers.host}`
    )

  const parts =
    url.pathname
      .split('/')
      .filter(Boolean)

  if (parts[0] !== 'requests') {
    return send(
      res,
      404,
      { message: 'Not found' }
    )
  }

  // Get all internship applications
  if (
    req.method === 'GET' &&
    parts.length === 1
  ) {
    return send(res, 200, requests)
  }

  // Get one internship application
  if (
    req.method === 'GET' &&
    parts[1]
  ) {

    const application =
      requests.find(
        item =>
          item.id === Number(parts[1])
      )

    return application
      ? send(res, 200, application)
      : send(
          res,
          404,
          { message: 'Application not found' }
        )
  }

  // Add internship application
  if (req.method === 'POST') {

    const application =
      await body(req)

    if (!application.companyName) {
      return send(
        res,
        400,
        { message: 'Company name is required' }
      )
    }

    if (!application.positionTitle) {
      return send(
        res,
        400,
        { message: 'Position title is required' }
      )
    }

    const newApplication = {
      id: nextId++,
      ...application,
      status:
        application.status || 'INTERESTED'
    }

    newApplication.priority =
      calculatePriority(newApplication)

    requests.push(newApplication)

    return send(
      res,
      201,
      newApplication
    )
  }

  // Update internship application
  if (
    req.method === 'PATCH' &&
    parts[1]
  ) {

    const application =
      requests.find(
        item =>
          item.id === Number(parts[1])
      )

    if (!application) {
      return send(
        res,
        404,
        { message: 'Application not found' }
      )
    }

    const updates =
      await body(req)

    Object.assign(
      application,
      updates
    )

    application.priority =
      calculatePriority(application)

    return send(
      res,
      200,
      application
    )
  }

  // Delete internship application
  if (
    req.method === 'DELETE' &&
    parts[1]
  ) {

    const before =
      requests.length

    requests =
      requests.filter(
        item =>
          item.id !== Number(parts[1])
      )

    return send(
      res,
      before === requests.length
        ? 404
        : 204
    )
  }

  return send(
    res,
    405,
    { message: 'Method not allowed' }
  )

}).listen(PORT, () => {

  console.log(
    `Internship Tracker API running at http://localhost:${PORT}`
  )
})