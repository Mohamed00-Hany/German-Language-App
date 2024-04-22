package com.projects.germanlanguageapp.data.dataSources.remote.apis

import com.projects.germanlanguageapp.domain.models.*
import retrofit2.http.*

interface WebServices {

    @GET("Level/Get")
    suspend fun getLevels():LevelsResponse
    @GET("Level/Post")
    suspend fun postLevels(@Query("levelName") levelName: String)

    @GET("adjective/Get")
    suspend fun getAdjectives(@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int):AdjectivesResponse
    @GET("adjective/Post")
    suspend fun PostAdjectives(@Query("adjectiveName") adjectiveName: String,@Query("AdjectiveTName") adjectiveTName: String,@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int)
    @GET("adjective/Put")
    suspend fun PutAdjectives(@Query("adjectiveId") adjectiveId: Int,@Query("adjectiveName") adjectiveName: String,@Query("AdjectiveTName") adjectiveTName: String)
    @GET("adjective/Delete")
    suspend fun DeleteAdjectives(@Query("adjectiveId") adjectiveId: Int)

    @GET("noun/Get")
    suspend fun getNouns(@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int):NounsResponse
    @GET("noun/Post")
    suspend fun Postnouns(@Query("NounName") NounName: String,@Query("NounTName") NounTName: String,@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int)
    @GET("noun/Put")
    suspend fun Putnouns(@Query("NounId") NounId: Int,@Query("NounName") NounName: String,@Query("NounTName") NounTName: String)
    @GET("noun/Delete")
    suspend fun Deletenouns(@Query("NounId") NounId: Int)

    @GET("verb/Get")
    suspend fun getVerbs(@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int):VerbsResponse
    @GET("verb/Post")
    suspend fun Postverbs(@Query("VerbName") VerbName: String,@Query("VerbTName") VerbTName: String,@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int)
    @GET("verb/Put")
    suspend fun Putverbs(@Query("aVerbId") VerbId: Int,@Query("VerbName") VerbName: String,@Query("VerbTName") VerbTName: String)
    @GET("verb/Delete")
    suspend fun Deleteverbs(@Query("VerbId") VerbId: Int)

    @GET("sentence/Get")
    suspend fun getOthers(@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int):OthersResponse
    @GET("sentence/Post")
    suspend fun PostOthers(@Query("SentenceName") SentenceName: String,@Query("SentenceTName") SentenceTName: String,@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int)
    @GET("sentence/Put")
    suspend fun PutOthers(@Query("SentenceId") SentenceId: Int,@Query("SentenceName") SentenceName: String,@Query("SentenceTName") SentenceTName: String)
    @GET("sentence/Delete")
    suspend fun DeleteOthers(@Query("SentenceId") SentenceId: Int)

    @GET("rearrange/Get")
    suspend fun getRearrangeQuestions(@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int):RearrangeResponse
    @GET("rearrange/Post")
    suspend fun PostRearrangeQuestions(@Query("RearrangeNameQuestion") RearrangeNameQuestion: String,@Query("RearrangeNameAnswer") RearrangeNameAnswer: String,@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int)
    @GET("rearrange/Put")
    suspend fun PutRearrangeQuestions(@Query("RearrangeId") RearrangeId: Int,@Query("RearrangeNameQuestion") RearrangeNameQuestion: String,@Query("RearrangeNameAnswer") RearrangeNameAnswer: String)
    @GET("rearrange/Delete")
    suspend fun DeleteRearrangeQuestions(@Query("RearrangeId") RearrangeId: Int)

    @GET("complete/Get")
    suspend fun getCompleteQuestions(@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int):CompleteResponse
    @GET("complete/Post")
    suspend fun PostCompleteQuestions(@Query("CompleteNameQuestion") CompleteNameQuestion: String,@Query("CompleteNameAnswer") CompleteNameAnswer: String,@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int)
    @GET("complete/Put")
    suspend fun PutCompleteQuestions(@Query("CompleteId") CompleteId: Int,@Query("CompleteNameQuestion") CompleteNameQuestion: String,@Query("CompleteNameAnswer") CompleteNameAnswer: String)
    @GET("complete/Delete")
    suspend fun DeleteCompleteQuestions(@Query("CompleteId") CompleteId: Int)

    @GET("choose/Get")
    suspend fun getChooseQuestions(@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int):ChooseResponse
    @GET("choose/Post")
    suspend fun PostChooseQuestions(@Query("ChooseNameQuestion") ChooseNameQuestion: String,@Query("ChooseNameAnswer") ChooseNameAnswer: String,@Query("ChooseNameAnswerRight") ChooseNameAnswerRight: Int,@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int)
    @GET("choose/Put")
    suspend fun PutChooseQuestions(@Query("ChooseId") ChooseId: Int,@Query("ChooseNameQuestion") ChooseNameQuestion: String,@Query("ChooseNameAnswer") ChooseNameAnswer: String,@Query("ChooseNameAnswerRight") ChooseNameAnswerRight: Int)
    @GET("choose/Delete")
    suspend fun DeleteChooseQuestions(@Query("ChooseId") ChooseId: Int)

    @GET("Match/Get")
    suspend fun getMatchQuestions(@Query("levelId") levelId: Int, @Query("lessonId") lessonId: Int):MatchResponse

    @GET("Match/Post")
    suspend fun postMatchQuestions(
        @Query("MatchNameQuestion1") MatchNameQuestion1: String,
        @Query("MatchNameAnswer1") MatchNameAnswer1: String,
        @Query("MatchNameQuestion2") MatchNameQuestion2: String,
        @Query("MatchNameAnswer2") MatchNameAnswer2: String,
        @Query("MatchNameQuestion3") MatchNameQuestion3: String,
        @Query("MatchNameAnswer3") MatchNameAnswer3: String,
        @Query("MatchNameQuestion4") MatchNameQuestion4: String,
        @Query("MatchNameAnswer4") MatchNameAnswer4: String,
        @Query("levelId") levelId: Int,
        @Query("lessonId") lessonId: Int
    )

    @GET("Match/Put")
    suspend fun putMatchQuestion(
        @Query("MatchId") MatchId: Int,
        @Query("MatchNameQuestion1") MatchNameQuestion1: String,
        @Query("MatchNameAnswer1") MatchNameAnswer1: String,
        @Query("MatchNameQuestion2") MatchNameQuestion2: String,
        @Query("MatchNameAnswer2") MatchNameAnswer2: String,
        @Query("MatchNameQuestion3") MatchNameQuestion3: String,
        @Query("MatchNameAnswer3") MatchNameAnswer3: String,
        @Query("MatchNameQuestion4") MatchNameQuestion4: String,
        @Query("MatchNameAnswer4") MatchNameAnswer4: String,

    )

    @GET("Match/Delete")
    suspend fun deleteMatchQuestion(@Query("MatchId") MatchId: Int)


}