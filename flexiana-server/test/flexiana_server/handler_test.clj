(ns flexiana-server.handler-test
  (:require
    [clojure.data.json :as json]
    [clojure.test :refer :all]
    [flexiana-server.handler :refer :all]
    [ring.mock.request :as mock]))


(deftest chars-in-string-test

  (testing "empty strings"
    (let [em1 (chars-in-string? "" "")
          em2 (chars-in-string? "ABC" "")
          em3 (chars-in-string? "" "ABC")]
      (is (not em1))
      (is (not em2))
      (is (not em3))))

  (testing "nil values"
    (let [ni1 (chars-in-string? nil nil)
          ni2 (chars-in-string? "ABC" nil)
          ni3 (chars-in-string? nil "ABC")]
      (is (not ni1))
      (is (not ni2))
      (is (not ni3))))

  (testing "positives"
    (let [po1 (chars-in-string? "AAB" "AAABBBCCCDDD")
          po2 (chars-in-string? "rab bit" "appleorangeblueberry ti")
          po3 (chars-in-string? "`'3456" "123456789`'!@#$%^&*(")]
      (is po1)
      (is po2)
      (is po3)))

  (testing "negatives"
    (let [ne1 (chars-in-string? "AAB" "ABBBCCCDDD")
          ne2 (chars-in-string? "rab bit" "appleorangeblueberryti")
          ne3 (chars-in-string? "`'34563456" "123456789`'!@#$%^&*(")]
      (is not ne1)
      (is not ne2)
      (is not ne3)))

  (testing "specification"
    (let [sp1 (chars-in-string? "AA" "ABCD")
          sp2 (chars-in-string? "AA" "BACAD")
          sp3 (chars-in-string? "AA" "ABAEAE")
          sp4 (chars-in-string? "AAB" "ABC")]
      (is (not sp1))
      (is sp2)
      (is sp2)
      (is (not sp4)))))


(deftest app-routes-test

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))))

  (testing "app-routes-positive"
    (let [http-resp (app (mock/request :get "/chars-in-string?str1=AA&str2=AABCD"))
          json-resp (json/read-str (:body http-resp))]
      (is (= (:status http-resp) 200))
      (is (contains? json-resp "result"))
      (is (= (get json-resp "result") true))))

  (testing "app-routes-negative"
    (let [http-resp (app (mock/request :get "/chars-in-string?str1=DAB&str2=BRAKA"))
          json-resp (json/read-str (:body http-resp))]
      (is (= (:status http-resp) 200))
      (is (contains? json-resp "result"))
      (is (= (get json-resp "result") false)))))
