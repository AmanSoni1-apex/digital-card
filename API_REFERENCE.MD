# Hotel Digital Card - API Reference

This file documents all available endpoints, their HTTP methods, payload formats, and example usage.

---

## 1. Create Card
**Endpoint**:  `POST /api/cards/create`

**Payload**: `(JSON Array of objects)`

json
[
  {
    "phoneNumber": "9876543",
    "amenities": ["WiFi", "Breakfast", "Gym"]
  }
]
**Response**:
json format
[
  {
    "sessionId": "1234-5678-90ab",
    "phoneNumber": "9876543210",
    "validFrom": "2025-09-23T14:09:14",
    "validTill": "2025-09-24T14:09:14",
    "amenities": ["WiFi", "Breakfast", "Gym"],
    "status": "ACTIVE"
  }
]

########################################


## 2. Get All Cards
**Endpoint**: `GET /api/cards`

**Response**: `Array of all cards in the system`.

########################################


## 3. Get Card by Session ID
**Endpoint**: `GET /api/cards/{sessionId}`

**Example**: `GET /api/cards/77f30d60-4558-4fc6-adee-0b44b1bf22f6`

########################################


## 4. Suspend / Activate a Card
**Endpoint**: `PUT /api/cards/{sessionId}/suspend?suspend=true`
`suspend=true → Suspend card`
`suspend=false → Activate card`

**Example**: `PUT /api/cards/77f30d60-4558-4fc6-adee-0b44b1bf22f6/suspend?suspend=true`

########################################

## 5. Generate QR Code for Card
**Endpoint**: `GET /api/cards/{sessionId}/qrcode`
**Example**: `GET /api/cards/77f30d60-4558-4fc6-adee-0b44b1bf22f6/qrcode`
**Response**: `Image (PNG Base64 / binary stream)`

`Notes`
`Always check HTTP method carefully (GET, POST, PUT).`
`For create, payload must be a JSON array [...] even if only one card is created.`
`Card suspension is controlled via the suspend query param.`