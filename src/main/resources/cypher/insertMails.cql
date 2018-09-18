merge (sender:User {id: {1}})
  on create set
    sender.name = {2},
    sender.age = {3},
    sender.address = {4}

merge (recipient:User {id: {5}})
  on create set
    recipient.name = {6},
    recipient.age = {7},
    recipient.address = {8}

merge (sender)-[mailed:SENT {id: {9}}]->(recipient)
  on create set
    mailed.mail_topic = {10},
    mailed.mail_body = {11},
    mailed.sent_date = {12},
    mailed.delivery_date = {13},
    mailed.status = {14}
